package com.nicoardizzoli.pruebatecnicabanco.controller;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/guardar")
    public ResponseEntity<String> saveCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = clienteService.saveCliente(clienteDTO);
        return new ResponseEntity<>("Cliente creado exitosamente, id: "+ cliente.getClienteId(),HttpStatus.CREATED);
    }
}
