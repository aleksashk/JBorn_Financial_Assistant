package com.philimonov.service;

import com.philimonov.converter.AccountModelToAccountDtoConverter;
import com.philimonov.dao.AccountDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountModelToAccountDtoConverter accountDtoConverter;

    public AccountService(AccountDao accountDao, AccountModelToAccountDtoConverter accountDtoConverter) {
        this.accountDao = accountDao;
        this.accountDtoConverter = accountDtoConverter;
    }

    public List<AccountDTO> findAllByPersonId(int personId) {
        return accountDao.findAllByPersonId(personId).stream().map(accountDtoConverter::convert).collect(Collectors.toList());
    }

    public AccountDTO insert(String name, long amount, int personId) {
        return Optional.ofNullable(accountDao.insert(name, amount, personId)).map(accountDtoConverter::convert).orElse(null);
    }

    public boolean delete(int id, int personId) {
        return accountDao.delete(id, personId);
    }
}
