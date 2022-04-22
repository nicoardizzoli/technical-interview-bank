package com.nicoardizzoli.pruebatecnicabanco.repository;

import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
