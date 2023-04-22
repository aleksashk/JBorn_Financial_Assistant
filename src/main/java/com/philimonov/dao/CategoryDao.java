package com.philimonov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.philimonov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public CategoryModel findByNameAndUserId(String name, long userId) {
        CategoryModel categoryModel = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from category " +
                            "where name = ? and user_id = ?"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categoryModel = new CategoryModel();
                categoryModel.setName(resultSet.getString("name"));
                categoryModel.setUserId(resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModel;
    }

    public CategoryModel create(String name, long userId) {
        CategoryModel categoryModel;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into category(name, user_id) " +
                            "values (?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, userId);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                categoryModel = new CategoryModel();
                categoryModel.setName(resultSet.getString(2));
                categoryModel.setUserId(resultSet.getLong(3));
                return categoryModel;
            } else {
                throw new CustomException("Invalid data. Category didn't create");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public CategoryModel update(long id, String newName, long userId) {
        CategoryModel categoryModel;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update category set name = ? " +
                             "where id = ? and user_id = ?")) {

            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.setLong(3, userId);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                categoryModel = new CategoryModel();
                categoryModel.setId(resultSet.getLong("id"));
                categoryModel.setName(resultSet.getString("name"));
                categoryModel.setUserId(resultSet.getLong("user_id"));
            } else {
                throw new CustomException("Invalid data. Category didn't update");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }

        return categoryModel;
    }

    public void delete(long id, long userId) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from category " +
                            "where id = ? and user_id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);

            if (preparedStatement.executeUpdate() != 1) {
                throw new CustomException("Invalid request. Category didn't delete.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public List<CategoryModel> getAllByUserId(long userId) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from category " +
                            "where user_id = ?"
            );
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CategoryModel categoryModel = new CategoryModel();

                categoryModel.setId(resultSet.getLong(1));
                categoryModel.setName(resultSet.getString(2));
                categoryModel.setUserId(resultSet.getLong(3));

                categoryModelList.add(categoryModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModelList;
    }
}
