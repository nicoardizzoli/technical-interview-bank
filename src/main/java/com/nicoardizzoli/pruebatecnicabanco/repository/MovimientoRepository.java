package com.nicoardizzoli.pruebatecnicabanco.repository;

import com.nicoardizzoli.pruebatecnicabanco.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
