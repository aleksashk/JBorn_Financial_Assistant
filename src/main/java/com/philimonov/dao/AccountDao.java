package com.philimonov.dao;

import com.philimonov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final DataSource dataSource;

    public AccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<AccountModel> findAllByPersonId(int personId) {
        List<AccountModel> accountList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "select * from account where person_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, personId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountModel accountModel = new AccountModel();
                accountModel.setId(rs.getInt("id"));
                accountModel.setName(rs.getString("name"));
                accountModel.setAmount(rs.getLong("amount"));
                accountModel.setPersonId(personId);
                accountList.add(accountModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountList;
    }

    public AccountModel insert(String name, long amount, int personId) {
        if (amount < 0) {
            throw new CustomException("Сумма на создаваемом счете не может быть отрицательной.");
        }
        AccountModel accountModel = null;
        try (Connection connection = dataSource.getConnection()) {
            String query = "insert into account (name, amount, person_id) values (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setLong(2, amount);
            ps.setInt(3, personId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                accountModel = new AccountModel();
                accountModel.setId(rs.getInt(1));
                accountModel.setName(name);
                accountModel.setAmount(amount);
                accountModel.setPersonId(personId);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountModel;
    }

    public boolean delete(int id, int personId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "delete from account where id = ? and person_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, personId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}
