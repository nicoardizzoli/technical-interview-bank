package com.nicoardizzoli.pruebatecnicabanco.controller;


import com.nicoardizzoli.pruebatecnicabanco.dto.CuentaDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import com.nicoardizzoli.pruebatecnicabanco.service.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping("/guardar")
    public ResponseEntity<String> saveCuenta(@RequestBody CuentaDTO cuentaDTO){
        Cuenta cuenta = cuentaService.saveCuenta(cuentaDTO);
        return new ResponseEntity<>("Cuenta creada exitosamente: "+ cuenta.getCuentaId(), HttpStatus.CREATED);
    }
}
