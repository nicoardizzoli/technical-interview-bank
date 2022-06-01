package com.nicoardizzoli.technicalinterviewbank.repository;

import com.nicoardizzoli.technicalinterviewbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(Integer accountNumber);
}
