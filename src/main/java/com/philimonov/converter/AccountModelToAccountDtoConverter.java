package com.philimonov.converter;

import com.philimonov.dao.AccountModel;
import com.philimonov.service.AccountDTO;
import org.springframework.stereotype.Service;

@Service
public class AccountModelToAccountDtoConverter implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(source.getId());
        accountDto.setName(source.getName());
        accountDto.setAmount(source.getAmount());
        return accountDto;
    }
}
