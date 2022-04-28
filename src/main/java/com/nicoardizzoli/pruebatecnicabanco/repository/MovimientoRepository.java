package com.nicoardizzoli.pruebatecnicabanco.repository;

import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport;
import com.nicoardizzoli.pruebatecnicabanco.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("select m from Movimiento m where day(m.fecha) = :day and month(m.fecha) = :month and year(m.fecha) = :year and m.cuenta.cuentaId = :cuentaId and m.tipoMovimiento = :tipoMovimiento")
    List<Movimiento> findMovimientosByTipoDateAndCuenta(@Param("tipoMovimiento") TipoMovimiento tipoMovimiento, @Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("cuentaId") Long cuentaId);

    List<Movimiento> findMovimientosByFechaBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    @Query("select new com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport(m.fecha, concat(m.cuenta.titular.nombre,' ', m.cuenta.titular.apellido), m.cuenta.numeroCuenta, m.cuenta.tipoCuenta, m.saldoInicialDeCuenta, m.cuenta.estado, m.valor, m.cuenta.saldoInicial) from Movimiento m where (m.fecha between :fecha1 and :fecha2) and m.cuenta.titular.clienteId = :clienteId")
    List<MovimientoReport> movimientosReportByFechaBetweenAndCliente(@Param("fecha1") LocalDateTime fecha1, @Param("fecha2") LocalDateTime fecha2, @Param("clienteId")String clienteId);




}
