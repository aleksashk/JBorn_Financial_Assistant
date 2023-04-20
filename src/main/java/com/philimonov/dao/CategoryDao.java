package com.philimonov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.List;

public class CategoryDao {
    private final DataSource dataSource;

    public CategoryDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("password");
        dataSource = new HikariDataSource(config);
    }

    public List<CategoryModel> findAllByPersonId(int personId){
        return null;
    }

    public CategoryModel insert(String name, int personId){
        return null;
    }

    public CategoryModel update(String name, int id, int personId){
        return null;
    }

    public boolean delete(int id, int personId){
        return true;
    }
}
