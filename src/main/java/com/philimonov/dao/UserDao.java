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

public class UserDao {
    private final DataSource dataSource;

    public UserDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("password");

        dataSource = new HikariDataSource(config);
    }

    public UserModel findByEmailAndHash(String email, String hash) {
        UserModel userModel = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from service_user " + "where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hash);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userModel = new UserModel();
                userModel.setId(resultSet.getLong("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return userModel;
    }

    public UserModel insert(String email, String hash) {

        try (Connection connection = dataSource.getConnection()) {
            if (isEmailExists(email)) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into service_user(email, password) " + "values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, hash);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    UserModel userModel = new UserModel();
                    userModel.setEmail(email);
                    userModel.setPassword(hash);
                    return userModel;
                } else {
                    throw new CustomException("Invalid data. New ID didn't generate.");
                }
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return null;
    }

    private boolean isEmailExists(String email) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select email from service_user");
            while (resultSet.next()) {
                if (resultSet.getString("email").equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}
