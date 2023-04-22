package com.philimonov.service;

import com.philimonov.converter.AccountModelToDtoConverter;
import com.philimonov.dao.AccountDao;
import com.philimonov.dao.AccountModel;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountModelToDtoConverter accountModelToDtoConverter;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.accountModelToDtoConverter = new AccountModelToDtoConverter();
    }

    public List<AccountDTO> getAllByUserId(long userId) {
        return accountDao.findAllByUserId(userId)
                .stream()
                .map(accountModelToDtoConverter::convert)
                .collect(toList());
    }

    public AccountDTO addAccount(String title, BigDecimal balance, long userId) {
        AccountModel accountModel;
        if (accountDao.findByTitleAndUserId(title, userId) == null) {
            accountModel = accountDao.create(title, balance.doubleValue(), userId);
            return accountModelToDtoConverter.convert(accountModel);
        }
        return null;
    }

    public void deleteAccount(long id, long userId) {
        accountDao.delete(id, userId);
    }
}
