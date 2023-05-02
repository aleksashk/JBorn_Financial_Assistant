package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionDao {
    private final DataSource dataSource;

    public TransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionModel insert(long amount, int fromAccountId, int toAccountId, List<Integer> categories, int personId) {
        if (amount < 0) {
            throw new CustomException("Сумма транзакции не может быть отрицательной.");
        }
        TransactionModel transactionModel;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String query;
            PreparedStatement ps;
            if (fromAccountId != 0) {
                query = "update account set amount = amount - ? where amount >= ? and id = ? and person_id = ?;";
                ps = connection.prepareStatement(query);
                ps.setLong(1, amount);
                ps.setLong(2, amount);
                ps.setInt(3, fromAccountId);
                ps.setInt(4, personId);
                if (ps.executeUpdate() != 1) {
                    throw new CustomException("Не удалось создать транзакцию.");
                }
            }
            if (toAccountId != 0) {
                query = "update account set amount = amount + ? where id = ? and person_id = ?;";
                ps = connection.prepareStatement(query);
                ps.setLong(1, amount);
                ps.setInt(2, toAccountId);
                ps.setInt(3, personId);
                if (ps.executeUpdate() != 1) {
                    throw new CustomException("Не удалось создать транзакцию.");
                }
            }
            query = "insert into transaction (amount, execution_date, from_account_id, to_account_id)" +
                    " values (?, ?, ?, ?);";
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, amount);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date executionDate = new Date();
            ps.setTimestamp(2, Timestamp.valueOf(dateFormat.format(executionDate)));
            if (fromAccountId == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, fromAccountId);
            }
            if (toAccountId == 0) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, toAccountId);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                transactionModel = new TransactionModel();
                transactionModel.setId(rs.getInt(1));
                transactionModel.setAmount(amount);
                transactionModel.setExecutionDate(executionDate);
                transactionModel.setFromAccountId(fromAccountId);
                transactionModel.setToAccountId(toAccountId);
            } else {
                throw new CustomException("Не удалось создать транзакцию.");
            }
            query = "insert into transaction_to_category (transaction_id, category_id) values (?, ?);";
            ps = connection.prepareStatement(query);
            for (Integer categoryId : categories) {
                ps.setInt(1, transactionModel.getId());
                ps.setInt(2, categoryId);
                if (ps.executeUpdate() != 1) {
                    throw new CustomException("Не удалось создать транзакцию.");
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new CustomException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return transactionModel;
    }
}
