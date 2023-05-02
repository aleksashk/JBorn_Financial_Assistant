package com.philimonov.dao;

import com.philimonov.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class PersonDao {
    private final DataSource dataSource;

    public PersonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PersonModel findByEmailAndHash(String email, String hash) {
        PersonModel personModel = null;
        try (Connection connection = dataSource.getConnection()) {
            String query = "select * from person where email = ? and password = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                personModel = new PersonModel();
                personModel.setId(rs.getInt("id"));
                personModel.setEmail(rs.getString("email"));
                personModel.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return personModel;
    }

    public PersonModel insert(String email, String hash) {
        PersonModel personModel = null;
        try (Connection connection = dataSource.getConnection()) {
            String query = "insert into person (email, password) values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, hash);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                personModel = new PersonModel();
                personModel.setId(rs.getInt(1));
                personModel.setEmail(email);
                personModel.setPassword(hash);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return personModel;
    }
}





















