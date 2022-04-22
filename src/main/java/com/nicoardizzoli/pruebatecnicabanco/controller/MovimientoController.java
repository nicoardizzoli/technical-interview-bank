package com.nicoardizzoli.pruebatecnicabanco.controller;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping("/guardar")
    public ResponseEntity<String> saveMovimiento(@RequestBody MovimientoDTO movimientoDTO, @RequestParam Long cuentaId){
        movimientoService.saveMovimiento(movimientoDTO, cuentaId);
        return new ResponseEntity<>("Movimiento generado exitosamente", HttpStatus.CREATED);
    }
}
