package com.nicoardizzoli.technicalinterviewbank.controller;


import com.nicoardizzoli.technicalinterviewbank.dto.AccountDTO;
import com.nicoardizzoli.technicalinterviewbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     *
     * @param accountDTO complete and valid accountDTO
     * @return a message of success with the id of the account
     */
    @Operation(summary = "Save a valid Account")
    @PostMapping("/save")
    public ResponseEntity<String> saveAccount(@Valid @RequestBody AccountDTO accountDTO){
        AccountDTO account = accountService.saveAccount(accountDTO);
        return new ResponseEntity<>("Account created successfully, account number: "+ account.getAccountNumber(), HttpStatus.CREATED);
    }

    /**
     *
     * @return All Accounts
     */
    @Operation(summary = "Get All Accounts")
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accountDTOS = accountService.getAllAcounts();
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }
}
