package com.nicoardizzoli.pruebatecnicabanco.repository;

import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport;
import com.nicoardizzoli.pruebatecnicabanco.model.TipoMovimiento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    //BUENA PRACTICA CUANDO ESCRIBIMOS UNA QUERY, LAS KEYWORDS DE SQL PONERLAS EN MAYUSCULA!
    @Query("SELECT m FROM Movimiento m WHERE DAY(m.fecha) = :day AND MONTH(m.fecha) = :month AND YEAR(m.fecha) = :year AND m.cuenta.cuentaId = :cuentaId AND m.tipoMovimiento = :tipoMovimiento")
    List<Movimiento> findMovimientosByTipoDateAndCuenta(@Param("tipoMovimiento") TipoMovimiento tipoMovimiento, @Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("cuentaId") Long cuentaId);

    List<Movimiento> findMovimientosByFechaBetween(LocalDateTime fecha1, LocalDateTime fecha2);

    @Query("SELECT new com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport(m.fecha,m.tipoMovimiento, CONCAT(m.cuenta.titular.nombre,' ', m.cuenta.titular.apellido), m.cuenta.numeroCuenta, m.cuenta.tipoCuenta, m.saldoInicialDeCuenta, m.cuenta.estado, m.valor, m.cuenta.saldoInicial) FROM Movimiento m WHERE (m.fecha BETWEEN :fecha1 AND :fecha2) AND m.cuenta.titular.clienteId = :clienteId")
    List<MovimientoReport> movimientosReportByFechaBetweenAndCliente(@Param("fecha1") LocalDateTime fecha1, @Param("fecha2") LocalDateTime fecha2, @Param("clienteId")String clienteId);

    //SI QUEREMOS POR EJEMPLO HACER UNA QUERY PARA BORRAR / MODIFICAR ALGO. HAY QUE AGREGARLE EL @Modifying y el @Transactional
    //IMPORTANTE! NO USAR LO PUSE DE EJEMPLO NADA MAS. (el INT que devuelve es el numero de rows borrados)
    @Transactional
    @Modifying
    @Query("DELETE FROM Movimiento m WHERE m.movimientoId = ?1")
    int deleteMOvimientoById(Long id);


    //PAGING AND SORTING
    //ACA A CUALQUIER METODO PODEMOS AGREGARLE COMO PARAMETRO Sort Y USARLO.
    @Query("SELECT new com.nicoardizzoli.pruebatecnicabanco.model.MovimientoReport(m.fecha, m.tipoMovimiento, CONCAT(m.cuenta.titular.nombre,' ', m.cuenta.titular.apellido), m.cuenta.numeroCuenta, m.cuenta.tipoCuenta, m.saldoInicialDeCuenta, m.cuenta.estado, m.valor, m.cuenta.saldoInicial) FROM Movimiento m WHERE (m.fecha BETWEEN :fecha1 AND :fecha2) AND m.cuenta.titular.clienteId = :clienteId")
    List<MovimientoReport> movimientosReportByFechaBetweenAndClienteSortedByFechaAsc(@Param("fecha1") LocalDateTime fecha1, @Param("fecha2") LocalDateTime fecha2, @Param("clienteId")String clienteId, Sort sort);



}
