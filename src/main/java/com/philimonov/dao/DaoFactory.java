package com.philimonov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoFactory {
    private static PersonDao personDao;
    private static AccountDao accountDao;
    private static CategoryDao categoryDao;
    private static TransactionDao transactionDao;

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(System.getProperty("jdbcUrl","jdbc:postgresql://localhost:5432/postgres"));
            config.setUsername(System.getProperty("jdbcUser", "postgres"));
            config.setPassword(System.getProperty("jdbcPassword","12345"));
            dataSource = new HikariDataSource(config);
            initDatabase();
        }
        return dataSource;
    }

    private static void initDatabase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(
                    System.getProperty("liquibaseFile", "liquibase.xml"),
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    public static PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao(getDataSource());
        }
        return personDao;
    }

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao(getDataSource());
        }
        return accountDao;
    }

    public static CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao(getDataSource());
        }
        return categoryDao;
    }

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao(getDataSource());
        }
        return transactionDao;
    }

    public static void setDataSourceToNull() {
        dataSource = null;
    }

    public static void setPersonDaoToNull() {
        personDao = null;
    }

    public static void setAccountDaoToNull() {
        accountDao = null;
    }

    public static void setCategoryDaoToNull() {
        categoryDao = null;
    }

    public static void setTransactionDaoToNull() {
        transactionDao = null;
    }
}

