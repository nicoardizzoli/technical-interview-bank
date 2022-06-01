package com.nicoardizzoli.technicalinterviewbank.mapper;

import com.nicoardizzoli.technicalinterviewbank.dto.AccountDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface AccountMapper {

    @Mapping(source = "holder.identification", target = "customerIdentification")
    AccountDTO accountToDto(Account account);

    Account dtoToAccount(AccountDTO accountDTO);
}
