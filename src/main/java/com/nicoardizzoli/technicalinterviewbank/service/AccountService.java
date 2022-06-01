package com.nicoardizzoli.technicalinterviewbank.service;

import com.nicoardizzoli.technicalinterviewbank.dto.AccountDTO;
import com.nicoardizzoli.technicalinterviewbank.exception.FoundException;
import com.nicoardizzoli.technicalinterviewbank.mapper.AccountMapper;
import com.nicoardizzoli.technicalinterviewbank.model.Account;
import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import com.nicoardizzoli.technicalinterviewbank.repository.AccountRepository;
import com.nicoardizzoli.technicalinterviewbank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    public AccountDTO saveAccount(AccountDTO accountDTO) {
        Customer holderByCustomerId = customerRepository.findCustomerByIdentification(accountDTO.getCustomerIdentification())
                .orElseThrow(() -> new FoundException(String.format("The customer with identification %s does not exist", accountDTO.getCustomerIdentification())));
        Account account = accountMapper.dtoToAccount(accountDTO);
        account.setHolder(holderByCustomerId);
        account.setAccountNumber(getRandomNumber() + holderByCustomerId.getAge());
        Account savedAccount = accountRepository.save(account);
        accountDTO.setAccountNumber(savedAccount.getAccountNumber());
        return accountDTO;
    }

    private int getRandomNumber() {
        int number = new Random().nextInt(999999999);
        return Integer.parseInt(String.format("%06d", number));
    }

    public List<AccountDTO> getAllAcounts() {
       return accountRepository.findAll().stream().map(accountMapper::accountToDto).collect(Collectors.toList());
    }
}
