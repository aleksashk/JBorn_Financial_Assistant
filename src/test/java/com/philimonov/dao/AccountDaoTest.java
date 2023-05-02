package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class AccountDaoTest {

    AccountDao accountDao;
    ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID());
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_account_dao_test.xml");
        context = new AnnotationConfigApplicationContext("com.philimonov");
        accountDao = context.getBean(AccountDao.class);
    }

    @Test
    public void findAllByPersonIdSuccess() {
        List<AccountModel> accounts = accountDao.findAllByPersonId(1);

        assertEquals("ABCDE12345", accounts.get(0).getName());
        assertEquals(100000L, accounts.get(0).getAmount());
        assertEquals(1, accounts.get(0).getPersonId());
    }

    @Test
    public void findAllByPersonIdFailed() {
        List<AccountModel> accounts = accountDao.findAllByPersonId(0);

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void insert() {
        AccountModel account = accountDao.insert("QWERT98765", 50000L, 1);

        assertEquals("QWERT98765", account.getName());
        assertEquals(50000L, account.getAmount());
        assertEquals(1, account.getPersonId());
    }

    @Test(expected = CustomException.class)
    public void insert_personIdIsInvalid() {
        accountDao.insert("QWERT98765", 50000L, 0);
    }

    @Test(expected = CustomException.class)
    public void insert_amountIsNegative() {
        accountDao.insert("QWERT98765", -50000L, 1);
    }

    @Test(expected = CustomException.class)
    public void insert_nameAlreadyExists() {
        accountDao.insert("ABCDE12345", 50000L, 1);
    }

    @Test
    public void delete() {
        assertTrue(accountDao.delete(1, 1));
    }

    @Test
    public void delete_idIsInvalid() {
        assertFalse(accountDao.delete(0, 1));
    }

    @Test
    public void delete_personIdIsInvalid() {
        assertFalse(accountDao.delete(1, 0));
    }
}