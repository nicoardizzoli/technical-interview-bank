package com.nicoardizzoli.technicalinterviewbank.mapper;

import com.nicoardizzoli.technicalinterviewbank.dto.AccountDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface AccountMapper {

    AccountDTO accountToDto(Account account);

    Account dtoToAccount(AccountDTO accountDTO);
}
