package com.nicoardizzoli.technicalinterviewbank.controller;

import com.nicoardizzoli.technicalinterviewbank.dto.MovementDTO;
import com.nicoardizzoli.technicalinterviewbank.model.MovementReport;
import com.nicoardizzoli.technicalinterviewbank.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/v1/movements")
public class MovementController {

    private final MovementService movementService;

    /**
     *
     * @param movementDTO Valid movement, format of date: yyyy-MM-dd' 'HH:mm:ss, example: 2022-05-30 05:18:24
     * @return a message of success
     */
    @Operation(summary = "Save a movement on an account with id accountId")
    @PostMapping("/save")
    public ResponseEntity<String> saveMovement(@Valid @RequestBody MovementDTO movementDTO) {
        movementService.saveMovement(movementDTO);
        return new ResponseEntity<>("Movement saved successfully", HttpStatus.CREATED);
    }

    /**
     * @param startDate startDate of the range to perform the search.  Format: yyyy-MM-dd' 'HH:mm:ss
     * @param endDate   endDate of the range to perform the search. Format: yyyy-MM-dd' 'HH:mm:ss
     * @return a list of movements between startDate and endDate
     */
    @Operation(summary = "Get movements between startDate and endDate")
    @GetMapping("/report")
    public ResponseEntity<List<MovementDTO>> getMovementBetweenDateRange(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime startDate,
                                                                         @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime endDate) {

        List<MovementDTO> movimientosBetweenRangoFechas = movementService.getMovementBetweenDateRange(startDate, endDate);
        return new ResponseEntity<>(movimientosBetweenRangoFechas, HttpStatus.OK);
    }

    /**
     * @param startDate  startDate of the range to perform the search.  Format: yyyy-MM-dd' 'HH:mm:ss
     * @param endDate    endDate of the range to perform the search.  Format: yyyy-MM-dd' 'HH:mm:ss
     * @param customerId id of the customer
     * @return a report of the movements from a customer in the range of dates
     */
    @Operation(summary = "Get movements of one customer between startDate and endDate")
    @GetMapping("/complete-report/{customerId}")
    public ResponseEntity<List<MovementReport>> getCompleteReport(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime startDate,
                                                                  @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime endDate,
                                                                  @PathVariable(name = "customerId") String customerId) {

        List<MovementReport> movementReportBetweenDates = movementService.getMovementReport(startDate, endDate, customerId);
        return new ResponseEntity<>(movementReportBetweenDates, HttpStatus.OK);
    }

    /**
     *
     * @param customerId id of the customer
     * @param startDate startDate of the range to perform the search.  Format: yyyy-MM-dd' 'HH:mm:ss
     * @param endDate end Date of the range to perform the search.  Format: yyyy-MM-dd' 'HH:mm:ss
     * @param sortBy optional - field to sort
     * @param sortDirection optional - ASC or DESC
     * @return a list of movements of one customer between range date sorted by sortBy and sortDirection
     */
    @Operation(summary = "Get a report of movements of one customer between range date sorted by sortBy and sortDirection")
    @GetMapping("/complete-report-ordered/{customerId}")
    public ResponseEntity<List<MovementReport>> getReporteSolicitadOrdenado(@PathVariable(name = "customerId") String customerId,
                                                                            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime startDate,
                                                                            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd' 'HH:mm:ss") LocalDateTime endDate,
                                                                            @RequestParam(name = "sortBy") Optional<String> sortBy,
                                                                            @RequestParam(name = "sortDirection") Optional<String> sortDirection) {

        List<MovementReport> movimientosBetweenRangoFechasOrdenado = movementService.getMovementReportSortedByDateAsc(startDate, endDate, customerId, sortBy, sortDirection);
        return new ResponseEntity<>(movimientosBetweenRangoFechasOrdenado, HttpStatus.OK);
    }

    /**
     *
     * @param pageNumber page to return
     * @param pageSize quantity of movements for page
     * @param sortBy field to sort the query
     * @return a page number pageNumber, with pageSize items, sorted by sortBy
     */
    @Operation(summary = "Get a report of all the movements, paginated")
    @GetMapping("/movements-page-sort")
    public ResponseEntity<Page<MovementDTO>> getMovimientosPageSort(@RequestParam(name = "pageNumber") Optional<Integer> pageNumber,
                                                                    @RequestParam(name = "pageSize") Optional<Integer> pageSize,
                                                                    @RequestParam(name = "sortBy") Optional<String> sortBy) {

        Page<MovementDTO> allMovimientos = movementService.getAllMovements(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(allMovimientos, HttpStatus.OK);
    }
}
