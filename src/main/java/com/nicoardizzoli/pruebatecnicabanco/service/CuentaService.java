package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.CuentaDTO;
import com.nicoardizzoli.pruebatecnicabanco.mapper.CuentaMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import com.nicoardizzoli.pruebatecnicabanco.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    private final CuentaMapper cuentaMapper;

    public Cuenta saveCuenta(CuentaDTO cuentaDTO){
        Cliente clienteByClienteId = clienteRepository.findClienteByClienteId(cuentaDTO.getTitular().getClienteId());
        Cuenta cuenta = cuentaMapper.dtoToCuenta(cuentaDTO);
        cuenta.setTitular(clienteByClienteId);
        return cuentaRepository.save(cuenta);
    }
}
