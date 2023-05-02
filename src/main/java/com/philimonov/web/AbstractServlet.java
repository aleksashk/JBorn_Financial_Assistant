package com.philimonov.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class AbstractServlet extends HttpServlet {
    public Optional<Integer> getPersonId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Optional.ofNullable((Integer) session.getAttribute("personId"));
    }

    public boolean checkString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public boolean checkArrayId(String[] array) {
        if (array == null) {
            return false;
        }
        for (String s : array) {
            if (!checkId(s)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkId(String id) {
        return id != null && id.matches("^[1-9]\\d*$");
    }

    public boolean checkAmount(String amount) {
        return amount != null && amount.matches("\\d+");
    }

    public Date getDate(String date) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}

