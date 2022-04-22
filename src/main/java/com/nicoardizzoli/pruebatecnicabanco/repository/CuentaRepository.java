package com.nicoardizzoli.pruebatecnicabanco.repository;

import com.nicoardizzoli.pruebatecnicabanco.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
