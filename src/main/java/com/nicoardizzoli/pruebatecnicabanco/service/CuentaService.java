package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.CuentaDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.FoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.CuentaMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import com.nicoardizzoli.pruebatecnicabanco.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@AllArgsConstructor
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    public Cuenta saveCuenta(CuentaDTO cuentaDTO) {
        Cliente clienteByClienteId = clienteRepository.findClienteByClienteId(cuentaDTO.getTitular().getClienteId())
                .orElseThrow(() -> new FoundException(String.format("El cliente con id %s no existe", cuentaDTO.getTitular().getClienteId())));

        Cuenta cuenta = cuentaMapper.dtoToCuenta(cuentaDTO);
        cuenta.setTitular(clienteByClienteId);
        cuenta.setNumeroCuenta(getRandomNumber());
        return cuentaRepository.save(cuenta);
    }

    private int getRandomNumber() {
        int number = new Random().nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }
}
