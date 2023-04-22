package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final DataSource dataSource;

    public AccountDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("password");

        dataSource = new HikariDataSource(config);
    }

    public List<AccountModel> findAllByUserId(long userId) {
        List<AccountModel> accountModelList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            AccountModel accountModel;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from account " +
                            "where user_id = ?"
            );
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountModel = new AccountModel();
                accountModel.setId(resultSet.getLong(1));
                accountModel.setTitle(resultSet.getString(2));
                accountModel.setBalance(new BigDecimal(resultSet.getLong(3)));
                accountModel.setUserId(resultSet.getLong(4));

                accountModelList.add(accountModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountModelList;
    }

    public AccountModel findByTitleAndUserId(String title, long userId) {
        AccountModel accountModel = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from account " +
                            "where title = ? and user_id = ?"
            );
            preparedStatement.setString(1, title);
            preparedStatement.setLong(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountModel = new AccountModel();
                accountModel.setId(resultSet.getLong(1));
                accountModel.setTitle(resultSet.getString(2));
                accountModel.setBalance(BigDecimal.valueOf(resultSet.getDouble(3)));
                accountModel.setUserId(resultSet.getLong(4));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountModel;
    }

    public AccountModel create(String title, double balance, long userId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into account(title, balance, user_id) " +
                            "values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, title);
            preparedStatement.setDouble(2, balance);
            preparedStatement.setLong(3, userId);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                AccountModel accountModel = new AccountModel();

                accountModel.setId(resultSet.getLong("id"));
                accountModel.setTitle(resultSet.getString("title"));
                accountModel.setBalance(BigDecimal.valueOf(resultSet.getDouble("balance")));
                accountModel.setUserId(resultSet.getLong("user_id"));
                return accountModel;
            } else {
                throw new CustomException("Invalid data. New ID didn't generate.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public void delete(long id, long userId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from account " +
                            "where id = ? and user_id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);

            if (preparedStatement.executeUpdate() != 1) {
                throw new CustomException("Invalid data. New ID didn't generate.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}
