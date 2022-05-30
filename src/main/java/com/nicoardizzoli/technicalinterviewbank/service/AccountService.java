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

import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    public AccountDTO saveAccount(AccountDTO accountDTO) {
        Customer holderByCustomerId = customerRepository.findCustomerByCustomerId(accountDTO.getCustomerId())
                .orElseThrow(() -> new FoundException(String.format("The customer with id %s does not exist", accountDTO.getCustomerId())));
        Account account = accountMapper.dtoToAccount(accountDTO);
        account.setHolder(holderByCustomerId);
        account.setAccountNumber(getRandomNumber());
        Account savedAccount = accountRepository.save(account);
        accountDTO.setAccountId(savedAccount.getAccountId());
        return accountDTO;
    }

    private int getRandomNumber() {
        int number = new Random().nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }
}
