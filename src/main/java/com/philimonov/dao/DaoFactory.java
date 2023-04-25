package com.philimonov.dao;

public class DaoFactory {
    private static PersonDao personDao;
    private static AccountDao accountDao;
    private static CategoryDao categoryDao;

    public static PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao();
        }
        return personDao;
    }

    public static AccountDao getAccountDao() {
        if(accountDao == null){
            accountDao = new AccountDao();
        }
        return accountDao;
    }

    public static CategoryDao getCategoryDao() {
        if(categoryDao == null){
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }
}

