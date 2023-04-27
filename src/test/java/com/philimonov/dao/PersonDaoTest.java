package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PersonDaoTest {
    PersonDao personDao;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID());
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_person_dao_test.xml");
        personDao = DaoFactory.getPersonDao();
    }

    @After
    public void reset() {
        DaoFactory.setPersonDaoToNull();
        DaoFactory.setDataSourceToNull();
    }

    @Test
    public void findByEmailAndHashSuccess() {
        PersonModel person = personDao.findByEmailAndHash("aleksandrphilimonov@gmail.com", "d8578edf8458ce06fbc5bb76a58c5ca4");

        assertEquals(1, person.getId());
        assertEquals("aleksandrphilimonov@gmail.com", person.getEmail());
        assertEquals("d8578edf8458ce06fbc5bb76a58c5ca4", person.getPassword());
    }

    @Test
    public void findByEmailAndHashFailed() {
        PersonModel person = personDao.findByEmailAndHash("aleksandrphilimonov@gmail.com", "d8578edf8458ce06fbc5bb76a58c5ca5");

        assertNull(person);
    }

    @Test(expected = CustomException.class)
    public void insertFailed() {
        personDao.insert("aleksandrphilimonov@gmail.com", "d8578edf8458ce06fbc5bb76a58c5ca4");
    }

    @Test
    public void insertSuccess() {
        PersonModel person = personDao.insert("aleksandrphilimonov@gmail.com", "d8578edf8458ce06fbc5bb76a58c5ca4");

        assertEquals(2, person.getId());
        assertEquals("aleksandrphilimonov@gmail.com", person.getEmail());
        assertEquals("d8578edf8458ce06fbc5bb76a58c5ca4", person.getPassword());
    }
}