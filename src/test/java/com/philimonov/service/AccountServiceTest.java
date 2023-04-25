package com.philimonov.service;

import com.philimonov.converter.AccountModelToAccountDtoConverter;
import com.philimonov.dao.AccountDao;
import com.philimonov.dao.AccountModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService service;
    @Mock
    AccountDao accountDao;
    @Mock
    AccountModelToAccountDtoConverter accountDtoConverter;

    int id = 1;
    String name = "SomeName";
    long amount = 300000L;
    int personId = 1;

    @Test
    public void findAllByPersonIdSuccess() {
        List<AccountModel> accountModelList = new ArrayList<>();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        int numberAccounts = 3;

        for (int i = 0; i < numberAccounts; i++) {
            AccountModel accountModel = new AccountModel();
            accountModel.setId(i + 1);
            accountModel.setName(name);
            accountModel.setAmount(amount);
            accountModel.setPersonId(personId);
            accountModelList.add(accountModel);

            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(i + 1);
            accountDTO.setName(name);
            accountDTO.setAmount(amount);
            accountDTOList.add(accountDTO);

            when(accountDtoConverter.convert(accountModelList.get(i))).thenReturn(accountDTOList.get(i));
        }
        when(accountDao.findAllByPersonId(personId)).thenReturn(accountModelList);

        List<AccountDTO> accountList = service.findAllByPersonId(personId);
        assertFalse(accountList.isEmpty());
        assertEquals(accountList, accountDTOList);
        verify(accountDao, times(1)).findAllByPersonId(personId);
        verify(accountDtoConverter, times(numberAccounts)).convert(anyObject());
    }

    @Test
    public void findAllByPersonIdFailed() {
        List<AccountModel> accountModelList = new ArrayList<>();
        when(accountDao.findAllByPersonId(personId)).thenReturn(accountModelList);
        List<AccountDTO> accountList = service.findAllByPersonId(personId);
        assertTrue(accountList.isEmpty());
        verify(accountDao, times(1)).findAllByPersonId(personId);
        verifyZeroInteractions(accountDtoConverter);
    }

    @Test
    public void insert() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(id);
        accountModel.setName(name);
        accountModel.setAmount(amount);
        accountModel.setPersonId(personId);
        when(accountDao.insert(name, amount, personId)).thenReturn(accountModel);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(id);
        accountModel.setName(name);
        accountModel.setAmount(amount);
        when(accountDtoConverter.convert(accountModel)).thenReturn(accountDTO);

        AccountDTO account = service.insert(name, amount, personId);
        assertNotNull(account);
        assertEquals(accountDTO, account);
        verify(accountDao, times(1)).insert(name, amount, personId);
        verify(accountDtoConverter, times(1)).convert(accountModel);
    }

    @Test
    public void delete() {
        when(accountDao.delete(id, personId)).thenReturn(true);
        assertTrue(service.delete(id, personId));
        verify(accountDao, times(1)).delete(id, personId);
    }
}