package com.philimonov.dao;

import com.philimonov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryDao {
    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CategoryModel> findAllByPersonId(int personId) {
        List<CategoryModel> categoryList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "select * from category where person_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, personId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(rs.getInt("id"));
                categoryModel.setName(rs.getString("name"));
                categoryModel.setPersonId(rs.getInt("person_id"));
                categoryList.add(categoryModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryList;
    }

    public CategoryModel insert(String name, int personId) {
        CategoryModel categoryModel = null;
        try (Connection connection = dataSource.getConnection()) {
            String query = "insert into category (name, person_id) values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, personId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                categoryModel = new CategoryModel();
                categoryModel.setId(rs.getInt(1));
                categoryModel.setName(name);
                categoryModel.setPersonId(personId);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModel;
    }

    public CategoryModel update(String name, int id, int personId) {
        CategoryModel categoryModel = null;
        try (Connection connection = dataSource.getConnection()) {
            String query = "update category set name = ? where id = ? and person_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.setInt(3, personId);
            if (ps.executeUpdate() > 0) {
                categoryModel = new CategoryModel();
                categoryModel.setId(id);
                categoryModel.setName(name);
                categoryModel.setPersonId(personId);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModel;
    }

    public boolean delete(int id, int personId) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "delete from category where id = ? and person_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, personId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public List<ReportCategoryModel> getExpenseReportByCategory(Date from, Date to, int personId) {
        List<ReportCategoryModel> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT c.name, sum(t.amount) AS amount FROM category AS c" +
                    " JOIN transaction_to_category AS ttc ON c.id = ttc.category_id" +
                    " JOIN transaction AS t ON ttc.transaction_id = t.id" +
                    " JOIN account AS a ON t.from_account_id = a.id" +
                    " WHERE t.execution_date BETWEEN ? AND ?" +
                    " AND c.person_id = ? GROUP BY c.name;";
            PreparedStatement ps = connection.prepareStatement(query);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ps.setTimestamp(1, Timestamp.valueOf(dateFormat.format(from)));
            ps.setTimestamp(2, Timestamp.valueOf(dateFormat.format(to)));
            ps.setInt(3, personId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportCategoryModel reportModel = new ReportCategoryModel();
                reportModel.setName(rs.getString("name"));
                reportModel.setAmount(rs.getLong("amount"));
                result.add(reportModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return result;
    }

    public List<ReportCategoryModel> getIncomeReportByCategory(Date from, Date to, int personId) {
        List<ReportCategoryModel> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT c.name, sum(t.amount) AS amount FROM category AS c" +
                    " JOIN transaction_to_category AS ttc ON c.id = ttc.category_id" +
                    " JOIN transaction AS t ON ttc.transaction_id = t.id" +
                    " JOIN account AS a ON t.to_account_id = a.id" +
                    " WHERE t.execution_date BETWEEN ? AND ?" +
                    " AND c.person_id = ? GROUP BY c.name;";
            PreparedStatement ps = connection.prepareStatement(query);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ps.setTimestamp(1, Timestamp.valueOf(dateFormat.format(from)));
            ps.setTimestamp(2, Timestamp.valueOf(dateFormat.format(to)));
            ps.setInt(3, personId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportCategoryModel reportModel = new ReportCategoryModel();
                reportModel.setName(rs.getString("name"));
                reportModel.setAmount(rs.getLong("amount"));
                result.add(reportModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return result;
    }
}
