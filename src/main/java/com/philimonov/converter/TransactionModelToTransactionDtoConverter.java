package com.philimonov.converter;

import com.philimonov.dao.TransactionModel;
import com.philimonov.service.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public class TransactionModelToTransactionDtoConverter implements Converter<TransactionModel, TransactionDTO> {
    @Override
    public TransactionDTO convert(TransactionModel source) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(source.getId());
        transactionDTO.setAmount(source.getAmount());
        transactionDTO.setExecutionDate(source.getExecutionDate());
        transactionDTO.setFromAccountId(source.getFromAccountId());
        transactionDTO.setToAccountId(source.getToAccountId());
        return transactionDTO;
    }
}
