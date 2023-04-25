package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public List<CategoryModel> findAllByPersonId(int personId) {
        return null;
    }

    public CategoryModel insert(String name, int personId) {
        return null;
    }

    public CategoryModel update(String name, int id, int personId) {
        return null;
    }

    public boolean delete(int id, int personId) {
        return true;
    }

    public List<ReportCategoryModel> getExpenseReportByCategory(Date from, Date to, int personId) {
        List<ReportCategoryModel> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "select c.name, sum(t.amount) from category as c" +
                    " join transaction_to_category as ttc on c.id = ttc.category_id" +
                    " join transaction as t on ttc.transaction_id = t.id" +
                    " join account as a on t.from_account_id = a.id" +
                    " where t.execution_date between ? and ?" +
                    " and c.person_id = ? group by c.name;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateFormat.format(from)));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateFormat.format(to)));
            preparedStatement.setInt(3, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReportCategoryModel reportCategoryModel = new ReportCategoryModel();
                reportCategoryModel.setName(resultSet.getString("name"));
                reportCategoryModel.setAmount(resultSet.getLong("amount"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return result;
    }

    public List<ReportCategoryModel> getIncomeReportByCategory(Date from, Date to, int personId) {
        List<ReportCategoryModel> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "select c.name, sum(t.amount) from category as c" +
                    " join transaction_to_category as ttc on c.id = ttc.category_id" +
                    " join transaction as t on ttc.transaction_id = t.id" +
                    " join account as a on t.to_account_id = a.id" +
                    " where t.execution_date between ? and ?" +
                    " and c.person_id = ? group by c.name;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateFormat.format(from)));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateFormat.format(to)));
            preparedStatement.setInt(3, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReportCategoryModel reportCategoryModel = new ReportCategoryModel();
                reportCategoryModel.setName(resultSet.getString("name"));
                reportCategoryModel.setAmount(resultSet.getLong("amount"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return result;
    }
}
