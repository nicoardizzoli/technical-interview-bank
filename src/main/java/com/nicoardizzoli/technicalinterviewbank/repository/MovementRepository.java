package com.nicoardizzoli.technicalinterviewbank.repository;

import com.nicoardizzoli.technicalinterviewbank.model.Movement;
import com.nicoardizzoli.technicalinterviewbank.model.MovementReport;
import com.nicoardizzoli.technicalinterviewbank.model.MovementType;
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
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query("SELECT m " +
            "FROM Movement m " +
            "WHERE DAY(m.date) = :day " +
            "AND MONTH(m.date) = :month " +
            "AND YEAR(m.date) = :year " +
            "AND m.account.accountId = :accountId " +
            "AND m.movementType = :movementType"
    )
    List<Movement> findMovementsByTypeDateAndAccount(@Param("movementType") MovementType movementType, @Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("accountId") Long accountId);


    List<Movement> findMovementByDateBetween(LocalDateTime date1, LocalDateTime date2);

    @Query(
            "SELECT new com.nicoardizzoli.technicalinterviewbank.model.MovementReport(" +
                    "m.date,m.movementType, " +
                    "CONCAT(m.account.holder.name,' ', m.account.holder.surname), " +
                    "m.account.accountNumber, " +
                    "m.account.accountType, " +
                    "m.initialAccountBalance, " +
                    "m.account.state, " +
                    "m.amount, " +
                    "m.availableBalance) " +
            "FROM Movement m " +
            "WHERE (m.date BETWEEN :startDate AND :endDate) " +
            "AND m.account.holder.identification = :customerIdentification"
    )
    List<MovementReport> movementReportByDateBetweenAndCustomer(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("customerIdentification") String customerIdentification);


    @Transactional
    @Modifying
    @Query("DELETE FROM Movement m WHERE m.movementId = ?1")
    int deleteMovementById(Long id);



    @Query(
            "SELECT new com.nicoardizzoli.technicalinterviewbank.model.MovementReport(" +
                    "m.date, " +
                    "m.movementType, " +
                    "CONCAT(m.account.holder.name,' ', m.account.holder.surname), " +
                    "m.account.accountNumber, " +
                    "m.account.accountType, " +
                    "m.initialAccountBalance, " +
                    "m.account.state, " +
                    "m.amount, " +
                    "m.availableBalance) " +
            "FROM Movement m " +
            "WHERE (m.date BETWEEN :startDate AND :endDate) " +
            "AND m.account.holder.identification = :customerIdentification"
    )
    List<MovementReport> movementReportBydateBetweenAndClienteSortedBydateAsc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("customerIdentification") String customerIdentification, Sort sort);




}
