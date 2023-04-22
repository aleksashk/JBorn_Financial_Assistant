package com.philimonov.converter;

import com.philimonov.dao.AccountModel;
import com.philimonov.service.AccountDTO;

public class AccountModelToDtoConverter implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(source.getId());
        accountDTO.setTitle(source.getTitle());
        accountDTO.setBalance(source.getBalance());
        accountDTO.setUserId(source.getUserId());

        return accountDTO;
    }
}
