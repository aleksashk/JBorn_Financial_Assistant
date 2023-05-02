package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TransactionDaoTest {

    TransactionDao transactionDao;
    AccountDao accountDao;
    ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID());
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_transaction_dao_test.xml");
        context = new AnnotationConfigApplicationContext("com.philimonov");
        transactionDao = context.getBean(TransactionDao.class);
        accountDao = context.getBean(AccountDao.class);
    }

    @Test
    public void insertFromAccountToAccountSuccess() {
        TransactionModel transaction = transactionDao.insert(
                20000L, 1, 2,
                Collections.singletonList(1), 1);

        assertEquals(1, transaction.getId());
        assertEquals(20000L, transaction.getAmount());
        assertEquals(1, transaction.getFromAccountId());
        assertEquals(2, transaction.getToAccountId());
    }

    @Test
    public void insertFromAccountOnlySuccess() {
        TransactionModel transaction = transactionDao.insert(
                20000L, 1, 0,
                Collections.singletonList(2), 1);

        assertEquals(1, transaction.getId());
        assertEquals(20000L, transaction.getAmount());
        assertEquals(1, transaction.getFromAccountId());
        assertEquals(0, transaction.getToAccountId());
    }

    @Test
    public void insertToAccountOnlySuccess() {
        TransactionModel transaction = transactionDao.insert(
                20000L, 0, 2,
                Collections.singletonList(3), 1);

        assertEquals(1, transaction.getId());
        assertEquals(20000L, transaction.getAmount());
        assertEquals(0, transaction.getFromAccountId());
        assertEquals(2, transaction.getToAccountId());
    }

    @Test(expected = CustomException.class)
    public void insert_fromAccountToAccountInvalid() {
        transactionDao.insert(20000L, 1, 1000,
                Collections.singletonList(1), 1);
    }

    @Test(expected = CustomException.class)
    public void insertAmountIsNegative() {
        transactionDao.insert(-20000L, 1, 2, Collections.singletonList(1), 1);
    }

    @Test
    public void insertCheckRollbackWhenFromAccountIsInvalid() {
        List<AccountModel> accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountBeforeTransaction = accounts.get(0).getAmount();
        long destAmountBeforeTransaction = accounts.get(1).getAmount();
        try {
            transactionDao.insert(20000L, 1000, 2,
                    Collections.singletonList(1), 1);
        } catch (CustomException ignore) {
        }
        accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountAfterTransaction = accounts.get(0).getAmount();
        long destAmountAfterTransaction = accounts.get(1).getAmount();

        assertEquals(sourceAmountBeforeTransaction, sourceAmountAfterTransaction);
        assertEquals(destAmountBeforeTransaction, destAmountAfterTransaction);
    }

    @Test
    public void insert_checkRollbackWhenToAccountIsInvalid() {
        List<AccountModel> accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountBeforeTransaction = accounts.get(0).getAmount();
        long destAmountBeforeTransaction = accounts.get(1).getAmount();
        try {
            transactionDao.insert(20000L, 1, 1000,
                    Collections.singletonList(1), 1);
        } catch (CustomException ignore) {
        }
        accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountAfterTransaction = accounts.get(0).getAmount();
        long destAmountAfterTransaction = accounts.get(1).getAmount();

        assertEquals(sourceAmountBeforeTransaction, sourceAmountAfterTransaction);
        assertEquals(destAmountBeforeTransaction, destAmountAfterTransaction);
    }

    @Test
    public void insert_checkRollbackWhenCategoriesIsInvalid() {
        List<AccountModel> accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountBeforeTransaction = accounts.get(0).getAmount();
        long destAmountBeforeTransaction = accounts.get(1).getAmount();
        try {
            transactionDao.insert(20000L, 1, 2,
                    Collections.singletonList(0), 1);
        } catch (CustomException ignore) {
        }
        accounts = accountDao.findAllByPersonId(1);
        accounts.sort(Comparator.comparingInt(AccountModel::getId));
        long sourceAmountAfterTransaction = accounts.get(0).getAmount();
        long destAmountAfterTransaction = accounts.get(1).getAmount();

        assertEquals(sourceAmountBeforeTransaction, sourceAmountAfterTransaction);
        assertEquals(destAmountBeforeTransaction, destAmountAfterTransaction);
    }
}