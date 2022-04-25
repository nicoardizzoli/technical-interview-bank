package com.nicoardizzoli.pruebatecnicabanco.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping("/guardar")
    public ResponseEntity<String> saveMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO, @RequestParam Long cuentaId) {
        movimientoService.saveMovimiento(movimientoDTO, cuentaId);
        return new ResponseEntity<>("Movimiento generado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<MovimientoDTO>> getMovimientosBetweenRangoFechas(@RequestParam(name = "fecha1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha1  ,
                                                                                @RequestParam(name = "fecha2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha2) {

        List<MovimientoDTO> movimientosBetweenRangoFechas = movimientoService.getMovimientosBetweenRangoFechas(fecha1, fecha2);
        return new ResponseEntity<>(movimientosBetweenRangoFechas, HttpStatus.OK);
    }
}
