package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDao {
    private final DataSource dateSource;

    public PersonDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("password");

        dateSource = new HikariDataSource(config);
    }

    public PersonModel findByEmailAndHash(String email, String hash) {
        PersonModel personModel = null;
        try (Connection connection = dateSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from service_user where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hash);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personModel = new PersonModel();
                personModel.setId(resultSet.getInt("id"));
                personModel.setEmail(resultSet.getString("email"));
                personModel.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return personModel;
    }

    public PersonModel insert(String email, String hash) {
        PersonModel personModel = null;
        try (Connection connection = dateSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into service_user(email, password) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hash);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                personModel = new PersonModel();
                personModel.setId(resultSet.getInt(1));
                personModel.setEmail(resultSet.getString(2));
                personModel.setPassword(hash);
                return personModel;
            }else {
                throw new CustomException("Can't generated id.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}





















