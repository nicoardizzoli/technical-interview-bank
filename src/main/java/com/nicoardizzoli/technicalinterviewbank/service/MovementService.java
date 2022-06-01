package com.nicoardizzoli.technicalinterviewbank.service;

import com.nicoardizzoli.technicalinterviewbank.dto.MovementDTO;
import com.nicoardizzoli.technicalinterviewbank.exception.ApiRequestException;
import com.nicoardizzoli.technicalinterviewbank.exception.NotFoundException;
import com.nicoardizzoli.technicalinterviewbank.mapper.MovementMapper;
import com.nicoardizzoli.technicalinterviewbank.model.Account;
import com.nicoardizzoli.technicalinterviewbank.model.Movement;
import com.nicoardizzoli.technicalinterviewbank.model.MovementReport;
import com.nicoardizzoli.technicalinterviewbank.model.MovementType;
import com.nicoardizzoli.technicalinterviewbank.repository.AccountRepository;
import com.nicoardizzoli.technicalinterviewbank.repository.MovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MovementMapper movementMapper;


    public void saveMovement(MovementDTO movementDTO) {
        Account accountFound = accountRepository.findAccountByAccountNumber(movementDTO.getAccountNumber()).orElseThrow(() -> new NotFoundException("Account not found"));
        Movement movement = movementMapper.dtoToMovement(movementDTO);
        movement.setInitialAccountBalance(accountFound.getBalance());
        this.checkMovement(movement);
        BigDecimal finalAmount = accountFound.getBalance().add(movement.getAmount());
        accountFound.setBalance(finalAmount);
        movement.setAvailableBalance(finalAmount);
        accountFound.addMovement(movement);
        accountRepository.save(accountFound);
    }

    public void checkMovement(Movement movement) {

        boolean isWithdraw = movement.getMovementType().equals(MovementType.WITHDRAW);
        boolean isDeposit = movement.getMovementType().equals(MovementType.DEPOSIT);

        if (isWithdraw && movement.getAmount().intValue() > 0)
            throw new ApiRequestException("The movement is a WITHDRAW, the amount should be negative");
        if (isDeposit && movement.getAmount().intValue() < 0)
            throw new ApiRequestException("The movement is a DEPOSIT, the amount should be positive");

        if (isWithdraw) {
            this.checkDiaryLimit(movement);
            this.checkAccountBalance(movement);
        }

    }

    public void checkDiaryLimit(Movement movement) {
        List<Movement> movementByDateAndAccount = movementRepository.findMovementsByTypeDateAndAccount(MovementType.WITHDRAW, movement.getDate().getDayOfMonth(), movement.getDate().getMonthValue(), movement.getDate().getYear(), movement.getAccount().getAccountId());
        BigDecimal usedBalance = movementByDateAndAccount.stream()
                .map(Movement::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        if (usedBalance.add(movement.getAmount()).negate().compareTo(movement.getAccount().getWithdrawLimit()) > 0) {
            throw new ApiRequestException("Diary withdraw limit exceeded, you can withdraw $" + movement.getAccount().getWithdrawLimit().subtract(usedBalance.negate()));
        }
    }

    public void checkAccountBalance(Movement movement) {
        if (movement.getAmount().compareTo(movement.getAccountBalance()) > 0)
            throw new ApiRequestException("You don't have enough balance to carry out the operation");
    }

    public List<MovementDTO> getMovementBetweenDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) throw new ApiRequestException("Start date required");
        if (endDate == null) throw new ApiRequestException("End date required");

        return movementRepository.findMovementByDateBetween(startDate, endDate).stream()
                .map(movementMapper::movementToDto)
                .toList();
    }


    public List<MovementReport> getMovementReport(LocalDateTime startDate, LocalDateTime endDate, String customerIdentification) {
        if (startDate == null) throw new ApiRequestException("Start date required");
        if (endDate == null) throw new ApiRequestException("End date required");
        if (customerIdentification == null || customerIdentification.isBlank()) throw new ApiRequestException("Customer identification required");

        return movementRepository.movementReportByDateBetweenAndCustomer(startDate, endDate, customerIdentification);
    }

    public List<MovementReport> getMovementReportSortedByDateAsc(LocalDateTime startDate, LocalDateTime endDate, String customerIdentification, Optional<String> sortBy, Optional<String> sortDirection) {
        if (startDate == null) throw new ApiRequestException("Start date required");
        if (endDate == null) throw new ApiRequestException("End date required");
        if (customerIdentification == null || customerIdentification.isBlank()) throw new ApiRequestException("Customer identification required");


        Sort sort = Sort.by(Sort.Direction.valueOf(sortDirection.orElse("ASC")), sortBy.orElse("date"));
        return movementRepository.movementReportBydateBetweenAndClienteSortedBydateAsc(startDate, endDate, customerIdentification, sort);
    }

    //USO DE PAGINACION Y SORT.
    //Aca desde el frontend nos pueden mandar la pagina que quieren y la cantidad por pagina y ordenado por que criterio
    public Page<MovementDTO> getAllMovements(Optional<Integer> pageNumber, Optional<Integer> pageSize, Optional<String> sortBy) {
        Sort sortFecha = Sort.by(sortBy.orElse("fecha")).ascending();

        //la pagina 0 seria la primer pagina, el size es el numero de campos q entran en la pag
        PageRequest pageRequest = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5), sortFecha);
        return movementRepository.findAll(pageRequest).map(movementMapper::movementToDto);

    }
}
