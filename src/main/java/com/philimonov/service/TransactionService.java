package com.philimonov.service;

import com.philimonov.converter.Converter;
import com.philimonov.dao.TransactionDao;
import com.philimonov.dao.TransactionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionDao transactionDao;
    private final Converter<TransactionModel, TransactionDTO> transactionDtoConverter;

    public TransactionService(TransactionDao transactionDao, Converter<TransactionModel, TransactionDTO> transactionDtoConverter) {
        this.transactionDao = transactionDao;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public TransactionDTO insert(long amount, int fromAccountId, int toAccountId, List<Integer> categories, int personId) {
        return transactionDtoConverter.convert(transactionDao.insert(amount, fromAccountId, toAccountId, categories, personId));
    }
}
