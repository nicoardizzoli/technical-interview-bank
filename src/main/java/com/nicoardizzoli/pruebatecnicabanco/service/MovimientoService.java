package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.ApiRequestException;
import com.nicoardizzoli.pruebatecnicabanco.exception.NotFoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.MovimientoMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import com.nicoardizzoli.pruebatecnicabanco.model.TipoMovimiento;
import com.nicoardizzoli.pruebatecnicabanco.repository.CuentaRepository;
import com.nicoardizzoli.pruebatecnicabanco.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;


    public void saveMovimiento(MovimientoDTO movimientoDTO, Long cuentaId) {
        Cuenta cuentaEncontrada = cuentaRepository.findById(cuentaId).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        Movimiento movimiento = movimientoMapper.dtoToMovimiento(movimientoDTO);
        cuentaEncontrada.addMovimiento(movimiento);
        this.chequearMovimiento(movimiento, cuentaEncontrada);
        cuentaRepository.save(cuentaEncontrada);
    }

    public void chequearMovimiento(Movimiento movimiento, Cuenta cuentaEncontrada) {

        //preguntar si es mejor declarar lal variable o ponerla directo en el set.
        boolean esRetiro = movimiento.getTipoMovimiento().equals(TipoMovimiento.RETIRO);
        boolean esDeposito = movimiento.getTipoMovimiento().equals(TipoMovimiento.DEPOSITO);

        //si es retiro o deposito y nos llega mal el valor desde el cliente
        if (esRetiro && movimiento.getValor().intValue() > 0) throw new ApiRequestException("El movimiento es un RETIRO por lo que el valor tiene que ser negativo");
        if (esDeposito && movimiento.getValor().intValue() < 0) throw new ApiRequestException("El movimiento es un DEPOSITO por lo que el valor tiene que ser positivo");

        if (esRetiro) {
            this.chequearPosibleMovimientoSegunLimiteDiario(movimiento);
            this.chequearPosibleMovimientoSegunSaldoEnCuenta(movimiento);
            cuentaEncontrada.getTope().add(movimiento.getValor());
        }

        BigDecimal saldoCuentaFinal = cuentaEncontrada.getSaldoInicial().add(movimiento.getValor());
        cuentaEncontrada.setSaldoInicial(saldoCuentaFinal);
    }

    public void chequearPosibleMovimientoSegunLimiteDiario(Movimiento movimiento) {
        List<Movimiento> movimientosByDateAndCuenta = movimientoRepository.findMovimientosByTipoDateAndCuenta(TipoMovimiento.RETIRO,movimiento.getFecha().getDayOfMonth(), movimiento.getFecha().getMonthValue(), movimiento.getFecha().getYear(), movimiento.getCuenta().getCuentaId());
        BigDecimal saldoUtilizado = movimientosByDateAndCuenta.stream()
                .map(Movimiento::getValor)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        //En el compareTo: si el n1 es > al que estamos comparando = 1
        //si yo utilice 1000 y quiero sacar 1100 tiene que entrar.
        //El valor lo negamos ya que siempre nos llega un numero negativo, pero para hacer la comparacion deberia ser +
        //1100.compareTo(1000) = 1
        //por lo tanto: el saldo de los retiros del dia + el valor del movimiento que queremos hacer tiene que ser menor al tope de retiro
        if (saldoUtilizado.add(movimiento.getValor()).negate().compareTo(movimiento.getCuenta().getTope()) > 0)
            throw new ApiRequestException("Limite diario excedido");
    }

    public void chequearPosibleMovimientoSegunSaldoEnCuenta(Movimiento movimiento) {
        if (movimiento.getValor().compareTo(movimiento.getSaldoCuenta()) > 0)
            throw new ApiRequestException("No dispone del saldo suficiente para realizar la operacion");
    }

    public List<MovimientoDTO> getMovimientosBetweenRangoFechas(LocalDateTime fecha1, LocalDateTime fecha2){
       if (fecha1 == null) throw new ApiRequestException("Fecha desde requerida");
       if (fecha2 == null) throw new ApiRequestException("Fecha hasta requerida");
        List<Movimiento> movimientosByFechaBetween = movimientoRepository.findMovimientosByFechaBetween(fecha1, fecha2);
        return movimientosByFechaBetween.stream().map(movimientoMapper::movimientoToDto).toList();
    }
}
