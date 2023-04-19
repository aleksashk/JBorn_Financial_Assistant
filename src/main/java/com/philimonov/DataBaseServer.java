package com.philimonov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class DataBaseServer {
    private final Map<String, String> users;
    private final String url;
    private final String userName;
    private final String password;
    private Connection connection;

    public DataBaseServer(String url, String userName, String password) {
        this.users = new HashMap<>();
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String logIn(String email, String password) {
        if (connection == null) {
            connectToDB();
        }
        String result;
        String query = "select * from service_user where email = ? and password = ?;";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, md5Hex(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                users.put(rs.getString("email"), rs.getString("password"));
                result = "Success";
            } else {
                result = "Access denied";
            }
        } catch (SQLException e) {
            result = e.getSQLState();
        }
        return result;
    }

    public String logUp(String email, String password) {
        if (connection == null) {
            connectToDB();
        }
        String result;
        String query = "insert into service_user(email, password) values(?,?);";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, md5Hex(password));

            if (ps.executeUpdate() > 0) {
                result = "Success";
            } else {
                result = "Access denied";
            }
        } catch (SQLException e) {
            result = e.getSQLState();
        }
        return result;
    }

    public void logOff(String email) {
        users.remove(email);
    }

    public int getPersonId(String email) {
        if (connection == null) {
            connectToDB();
        }
        String query = "select id from service_user where email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ignored) {
        }
        return -1;
    }

    public List<String> dataRetrievalQuery(String email, String[] columnList, String query, Object... values) {
        if (connection == null) {
            connectToDB();
        }
        List<String> resultList = new ArrayList<>();
        if (users.containsKey(email)) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                setValues(ps, values);
                ResultSet resultSet = ps.executeQuery();
                StringBuilder sb = new StringBuilder();
                while (resultSet.next()) {
                    for (String s : columnList) {
                        sb.append(resultSet.getString(s)).append(",");
                    }
                    resultList.add(sb.toString());
                }
            } catch (SQLException ignored) {
            }
        }
        return resultList;
    }

    public String dataChangeQuery(String email, String query, Object... values) {
        if (connection == null) {
            connectToDB();
        }
        String result = null;
        if (users.containsKey(email)) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                setValues(ps, values);
                if (ps.executeUpdate() > 0) {
                    result = "Success";
                }
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    private void setValues(PreparedStatement ps, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            ps.setObject(i + 1, values[i]);
        }
    }

    private void connectToDB() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
