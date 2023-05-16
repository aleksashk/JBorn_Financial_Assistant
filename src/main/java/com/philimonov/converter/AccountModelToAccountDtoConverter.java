package com.philimonov.converter;

import com.philimonov.dao.AccountModel;
import com.philimonov.service.AccountDTO;
import org.springframework.stereotype.Service;

@Service
public class AccountModelToAccountDtoConverter implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(source.getId());
        accountDTO.setName(source.getName());
        accountDTO.setAmount(source.getAmount());
        return accountDTO;
    }
}