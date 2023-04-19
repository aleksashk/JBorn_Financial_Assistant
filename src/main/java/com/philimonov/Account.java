package com.philimonov;

import java.math.BigDecimal;
import java.util.List;

public class Account {
    private final String email;
    private final DataBaseServer dataBaseServer;
    private final long userId;

    public Account(String email, DataBaseServer dataBaseServer) {
        this.email = email;
        this.dataBaseServer = dataBaseServer;
        this.userId = dataBaseServer.getPersonId(email);
    }

    public void showAccountsList() {
        String query = "select id, name, amount from account where person_id = ?;";
        String[] columns = {"id", "name", "amount"};

        List<String> resultQuery = dataBaseServer.dataRetrievalQuery(email, columns, query, userId);
        if (resultQuery.isEmpty()) {
            System.out.println("There is no accounts!");
            return;
        }
        System.out.printf("%-15s %-30s %s%n", "Id счета", "Имя счета", "Сумма на счете (в копейках)");
        for (String s : resultQuery) {
            String[] split = s.split(",");
            System.out.printf("%-15s %-30s %s%n", split[0], split[1], split[2]);
        }
    }

    public void create() {
        System.out.println("Enter an account name: ");
        String accountName = Tools.getNewLine();
        BigDecimal amount;
        while (true) {
            System.out.println("Input an amount: ");
            try {
                amount = new BigDecimal(Tools.getNewLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid data. Try again.");
            }
        }
        String query = "insert into account(name, amount, person_id) values(?, ?, ?);";
        String queryResult = dataBaseServer.dataChangeQuery(email, query, accountName, amount, userId);
        if ("Success".equalsIgnoreCase(queryResult)) {
            System.out.println("Success. The account created.");
        } else {
            System.out.println("Invalid data. " + queryResult);
        }
    }

    public void delete() {
        int accountId;
        while (true) {
            System.out.println("Enter accountId: ");
            try {
                accountId = Integer.parseInt(Tools.getNewLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid data! Try again!");
            }
        }
        String query = "delete from account where id = ? and  user_id = ?";
        String queryResult = dataBaseServer.dataChangeQuery(email, query, accountId, userId);
        if ("success".equalsIgnoreCase(queryResult)) {
            System.out.println("The account successfully deleted.");
        } else {
            System.out.println("Invalid data! " + queryResult);
        }
    }
}
