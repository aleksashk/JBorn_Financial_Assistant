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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DaoConfiguration {

    @Bean
    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/postgres"));
        config.setUsername(System.getProperty("jdbcUser", "postgres"));
        config.setPassword(System.getProperty("jdbcPassword", "12345"));
        DataSource dataSource = new HikariDataSource(config);
        initDatabase(dataSource);
        return dataSource;
    }

    private static void initDatabase(DataSource dataSource) {
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
}
