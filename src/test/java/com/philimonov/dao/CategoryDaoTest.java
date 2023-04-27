package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CategoryDaoTest {
    CategoryDao categoryDao;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl","jdbc:h2:mem:test_mem" + UUID.randomUUID());
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword","");
        System.setProperty("liquibaseFile","liquibase_category_dao_test.xml");
        categoryDao = DaoFactory.getCategoryDao();
    }

    @After
    public void reset() {
        DaoFactory.setCategoryDaoToNull();
        DaoFactory.setDataSourceToNull();
    }

    @Test
    public void findAllByPersonIdSuccess() {
        List<CategoryModel> categories = categoryDao.findAllByPersonId(1);

        assertEquals("from account to account", categories.get(0).getName());
        assertEquals(1, categories.get(0).getPersonId());
    }

    @Test
    public void findAllByPersonIdFailed() {
        List<CategoryModel> categories = categoryDao.findAllByPersonId(0);

        assertTrue(categories.isEmpty());
    }

    @Test
    public void insert() {
        CategoryModel category = categoryDao.insert("salary receipt", 1);

        assertEquals(2, category.getId());
        assertEquals("salary receipt", category.getName());
        assertEquals(1, category.getPersonId());
    }

    @Test(expected = CustomException.class)
    public void insert_nameAlreadyExists() {
        categoryDao.insert("from account to account", 1);
    }

    @Test(expected = CustomException.class)
    public void insert_personIdIsInvalid() {
        categoryDao.insert("salary receipt", 0);
    }

    @Test
    public void update() {
        CategoryModel category = categoryDao.update("purchase of goods", 1, 1);

        assertEquals(1, category.getId());
        assertEquals("purchase of goods", category.getName());
        assertEquals(1, category.getPersonId());
    }

    @Test
    public void update_idIsInvalid() {
        assertNull(categoryDao.update("purchase of goods", 0, 1));
    }

    @Test
    public void update_personIdIsInvalid() {
        assertNull(categoryDao.update("purchase of goods", 1, 0));
    }

    @Test
    public void delete() {
        assertTrue(categoryDao.delete(1, 1));
    }

    @Test
    public void delete_idIsInvalid() {
        assertFalse(categoryDao.delete(0, 1));
    }

    @Test
    public void delete_personIdIsInvalid() {
        assertFalse(categoryDao.delete(1, 0));
    }
}