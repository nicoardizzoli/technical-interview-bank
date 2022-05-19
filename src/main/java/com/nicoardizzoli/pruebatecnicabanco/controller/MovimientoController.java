package com.nicoardizzoli.pruebatecnicabanco.controller;

import com.nicoardizzoli.pruebatecnicabanco.dto.MovimientoDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport;
import com.nicoardizzoli.pruebatecnicabanco.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    /**
     *
     * @param fecha1 = fecha de inicio del rango de fechas // por ahi poner tambien el formato.
     * @param fecha2 = fecha de fin del rango de fechas
     * @return
     */
    @GetMapping("/reporteCompleto")
    public ResponseEntity<List<MovimientoDTO>> getMovimientosBetweenRangoFechas(@RequestParam(name = "fecha1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha1,
                                                                                @RequestParam(name = "fecha2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha2) {

        List<MovimientoDTO> movimientosBetweenRangoFechas = movimientoService.getMovimientosBetweenRangoFechas(fecha1, fecha2);
        return new ResponseEntity<>(movimientosBetweenRangoFechas, HttpStatus.OK);
    }

    @GetMapping("/reporteSolicitado")
    public ResponseEntity<List<MovimientoReport>> getReporteSolicitado(@RequestParam(name = "fecha1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha1,
                                                                       @RequestParam(name = "fecha2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha2,
                                                                       @RequestParam(name = "clienteId") String clienteId) {

        List<MovimientoReport> movimientosBetweenRangoFechas = movimientoService.getMovimientoReport(fecha1, fecha2, clienteId);
        return new ResponseEntity<>(movimientosBetweenRangoFechas, HttpStatus.OK);
    }

    @GetMapping("/reporteSolicitadoOrdenado")
    public ResponseEntity<List<MovimientoReport>> getReporteSolicitadOrdenado(@RequestParam(name = "fecha1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha1,
                                                                              @RequestParam(name = "fecha2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime fecha2,
                                                                              @RequestParam(name = "clienteId") String clienteId) {

        List<MovimientoReport> movimientosBetweenRangoFechasOrdenado = movimientoService.getMovimientoReportSortedByFechaAsc(fecha1, fecha2, clienteId);
        return new ResponseEntity<>(movimientosBetweenRangoFechasOrdenado, HttpStatus.OK);
    }

    @GetMapping("/movimientosPageSort")
    public ResponseEntity<Page<MovimientoDTO>> getMovimientosPageSort(@RequestParam(name = "pageNumber") Optional<Integer> pageNumber,
                                                                      @RequestParam(name = "pageSize") Optional<Integer> pageSize,
                                                                      @RequestParam(name = "sortBy") Optional<String> sortBy) {

        Page<MovimientoDTO> allMovimientos = movimientoService.getAllMovimientos(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(allMovimientos, HttpStatus.OK);
    }
}
