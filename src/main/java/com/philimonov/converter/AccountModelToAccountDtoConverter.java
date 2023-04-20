package com.philimonov.converter;

import com.philimonov.dao.AccountModel;
import com.philimonov.service.AccountDto;

public class AccountModelToAccountDtoConverter implements Converter<AccountModel, AccountDto>{
    @Override
    public AccountDto convert(AccountModel source) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(source.getId());
        accountDto.setName(source.getName());
        accountDto.setAmount(source.getAmount());
        return accountDto;
    }
}
