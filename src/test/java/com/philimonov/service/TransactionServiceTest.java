package com.philimonov.service;

import com.philimonov.converter.Converter;
import com.philimonov.dao.TransactionDao;
import com.philimonov.dao.TransactionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService service;
    @Mock
    TransactionDao transactionDao;
    @Mock
    Converter<TransactionModel, TransactionDTO> transactionDtoConverter;

    int id = 1;
    long amount = 50000L;
    Date date = new Date();
    int fromAccountId = 1;
    int toAccountId = 2;
    int personId = 1;

    @Test
    public void insert() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId(id);
        transactionModel.setAmount(amount);
        transactionModel.setExecutionDate(date);
        transactionModel.setFromAccountId(fromAccountId);
        transactionModel.setToAccountId(toAccountId);
        when(transactionDao.insert(amount, fromAccountId, toAccountId, Collections.singletonList(1), personId)).thenReturn(transactionModel);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setExecutionDate(date);
        transactionDTO.setFromAccountId(fromAccountId);
        transactionDTO.setToAccountId(toAccountId);
        when(transactionDtoConverter.convert(transactionModel)).thenReturn(transactionDTO);

        TransactionDTO transaction = service.insert(amount, fromAccountId, toAccountId, Collections.singletonList(1), personId);

        assertNotNull(transaction);
        assertEquals(transactionDTO, transaction);
        verify(transactionDao, times(1)).insert(amount, fromAccountId, toAccountId, Collections.singletonList(1), personId);
        verify(transactionDtoConverter, times(1)).convert(transactionModel);
    }
}