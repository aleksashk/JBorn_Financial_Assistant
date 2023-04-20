package com.philimonov.exception;

import java.sql.SQLException;

public class CustomException extends RuntimeException {
    public CustomException(SQLException e) {
        super(e);
    }

    public CustomException(String msg) {
        super(msg);
    }
}
