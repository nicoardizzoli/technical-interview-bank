package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.ApiRequestException;
import com.nicoardizzoli.pruebatecnicabanco.exception.NotFoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.MovimientoMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport;
import com.nicoardizzoli.pruebatecnicabanco.model.TipoMovimiento;
import com.nicoardizzoli.pruebatecnicabanco.repository.CuentaRepository;
import com.nicoardizzoli.pruebatecnicabanco.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;


    public void saveMovimiento(MovimientoDTO movimientoDTO, Long cuentaId) {
        Cuenta cuentaEncontrada = cuentaRepository.findById(cuentaId).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        Movimiento movimiento = movimientoMapper.dtoToMovimiento(movimientoDTO);
        movimiento.setSaldoInicialDeCuenta(cuentaEncontrada.getSaldoInicial());
        cuentaEncontrada.addMovimiento(movimiento);
        this.chequearMovimiento(movimiento, cuentaEncontrada);
        BigDecimal saldoCuentaFinal = cuentaEncontrada.getSaldoInicial().add(movimiento.getValor());
        cuentaEncontrada.setSaldoInicial(saldoCuentaFinal);
        cuentaRepository.save(cuentaEncontrada);
    }

    public void chequearMovimiento(Movimiento movimiento, Cuenta cuentaEncontrada) {

        //preguntar si es mejor declarar lal variable o ponerla directo en el set.
        boolean esRetiro = movimiento.getTipoMovimiento().equals(TipoMovimiento.RETIRO);
        boolean esDeposito = movimiento.getTipoMovimiento().equals(TipoMovimiento.DEPOSITO);

        //si es retiro o deposito y nos llega mal el valor desde el cliente
        if (esRetiro && movimiento.getValor().intValue() > 0)
            throw new ApiRequestException("El movimiento es un RETIRO por lo que el valor tiene que ser negativo");
        if (esDeposito && movimiento.getValor().intValue() < 0)
            throw new ApiRequestException("El movimiento es un DEPOSITO por lo que el valor tiene que ser positivo");

        if (esRetiro) {
            this.chequearPosibleMovimientoSegunLimiteDiario(movimiento);
            this.chequearPosibleMovimientoSegunSaldoEnCuenta(movimiento);
        }

    }

    public void chequearPosibleMovimientoSegunLimiteDiario(Movimiento movimiento) {
        List<Movimiento> movimientosByDateAndCuenta = movimientoRepository.findMovimientosByTipoDateAndCuenta(TipoMovimiento.RETIRO, movimiento.getFecha().getDayOfMonth(), movimiento.getFecha().getMonthValue(), movimiento.getFecha().getYear(), movimiento.getCuenta().getCuentaId());
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

    public List<MovimientoDTO> getMovimientosBetweenRangoFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        if (fecha1 == null) throw new ApiRequestException("Fecha desde requerida");
        if (fecha2 == null) throw new ApiRequestException("Fecha hasta requerida");

        return movimientoRepository.findMovimientosByFechaBetween(fecha1, fecha2).stream()
                .map(movimientoMapper::movimientoToDto)
                .toList();
    }


    public List<MovimientoReport> getMovimientoReport(LocalDateTime fecha1, LocalDateTime fecha2, String clienteId) {
        if (fecha1 == null) throw new ApiRequestException("Fecha desde requerida");
        if (fecha2 == null) throw new ApiRequestException("Fecha hasta requerida");
        if (clienteId == null || clienteId.isBlank()) throw new ApiRequestException("Cliente id requerido");

        return movimientoRepository.movimientosReportByFechaBetweenAndCliente(fecha1, fecha2, clienteId);
    }

    //USO DEL SORT
    public List<MovimientoReport> getMovimientoReportSortedByFechaAsc(LocalDateTime fecha1, LocalDateTime fecha2, String clienteId) {
        if (fecha1 == null) throw new ApiRequestException("Fecha desde requerida");
        if (fecha2 == null) throw new ApiRequestException("Fecha hasta requerida");
        if (clienteId == null || clienteId.isBlank()) throw new ApiRequestException("Cliente id requerido");

        //El sort tambien se puede combinar con un .and encadenado por si queremos ordenar por varios factores.
        // EJ, si hay 2 nombres q empiecen con A y uno tiene edad 18 y el otro 22, y poinemos q ordene por nombre y edad, si ponemos
        // edad desc van a estar los 2 nombres con A pero primero el de 22 y deps el de 18.
        Sort sortFecha = Sort.by(Sort.Direction.ASC, "fecha");
        return movimientoRepository.movimientosReportByFechaBetweenAndClienteSortedByFechaAsc(fecha1, fecha2, clienteId, sortFecha);
    }

    //USO DE PAGINACION Y SORT.
    //Aca desde el frontend nos pueden mandar la pagina que quieren y la cantidad por pagina y ordenado por que criterio
    public Page<MovimientoDTO> getAllMovimientos(Optional<Integer> pageNumber, Optional<Integer> pageSize, Optional<String> sortBy) {
        Sort sortFecha = Sort.by(sortBy.orElse("fecha")).ascending();

        //la pagina 0 seria la primer pagina, el size es el numero de campos q entran en la pag
        PageRequest pageRequest = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5), sortFecha);
        return movimientoRepository.findAll(pageRequest).map(movimientoMapper::movimientoToDto);

    }
}
