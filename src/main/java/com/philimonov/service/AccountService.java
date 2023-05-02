package com.philimonov.service;

import com.philimonov.converter.Converter;
import com.philimonov.dao.AccountDao;
import com.philimonov.dao.AccountModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {
    private final AccountDao accountDao;
    private final Converter<AccountModel, AccountDTO> accountDtoConverter;

    public AccountService(AccountDao accountDao, Converter<AccountModel, AccountDTO> accountDtoConverter) {
        this.accountDao = accountDao;
        this.accountDtoConverter = accountDtoConverter;
    }

    public List<AccountDTO> findAllByPersonId(int personId) {
        return accountDao.findAllByPersonId(personId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDTO insert(String name, long amount, int personId) {
        return Optional.ofNullable(accountDao.insert(name, amount, personId))
                .map(accountDtoConverter::convert)
                .orElse(null);
    }

    public boolean delete(int id, int personId) {
        return accountDao.delete(id, personId);
    }
}
