package com.nicoardizzoli.technicalinterviewbank.repository;

import com.nicoardizzoli.technicalinterviewbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
