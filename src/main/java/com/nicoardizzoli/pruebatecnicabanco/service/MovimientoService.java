package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.CuentaNotFoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.MovimientoMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.model.CuentaMovimiento;
import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import com.nicoardizzoli.pruebatecnicabanco.repository.CuentaRepository;
import com.nicoardizzoli.pruebatecnicabanco.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;


    public void saveMovimiento(MovimientoDTO movimientoDTO, Long cuentaId){
        Movimiento movimiento = movimientoMapper.dtoToMovimiento(movimientoDTO);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        Cuenta cuentaEncontrada = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));
        CuentaMovimiento cuentaMovimiento = new CuentaMovimiento(cuentaEncontrada, movimientoGuardado);
        cuentaEncontrada.addMovimiento(cuentaMovimiento);
        cuentaRepository.save(cuentaEncontrada);
    }
}
