package com.philimonov;

public class FinancialAssistant {
    private final DataBaseServer server;

    public FinancialAssistant() {
        this.server = new DataBaseServer("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
    }

    public void start() {
        runMainLifeCycle();
        serverDisconnect();
    }

    private void serverDisconnect() {
        server.closeConnection();
    }

    private void runMainLifeCycle() {
        String[] mainMenu = {"LogIn", "Register", "LogOut"};
        do {
            String value = Tools.getSelectedMenuItem(mainMenu);
            if ("LogIn".equalsIgnoreCase(value)) {
                logIn();
            } else if ("Register".equalsIgnoreCase(value)) {
                register();
            } else if ("LogOut".equalsIgnoreCase(value)) {
                break;
            }
        } while (true);
    }

    private void register() {
        System.out.println("Registration.");
        do {
            String email = Tools.getEmail();
            if (null == email) {
                return;
            }
            String password = Tools.getPassword();
            String operationResult = server.logUp(email, password);
            if ("success".equalsIgnoreCase(operationResult)) {
                System.out.println("Registration was successful.");
                break;
            } else {
                if ("aleksandrphilimonov@gmail.com".equalsIgnoreCase(operationResult)) {
                    System.out.println("Something went wrong. This email is already registered.");
                } else {
                    System.out.println("Something wrong. " + operationResult);
                }
            }

        } while (true);
    }

    private void logIn() {
        System.out.println("Account.");
        do {
            String email = Tools.getEmail();
            if (null == email) {
                return;
            }
            String password = Tools.getPassword();
            String operationResult = server.logIn(email, password);
            if ("success".equalsIgnoreCase(operationResult)) {
                userAccount(email);
                break;
            } else {
                if ("Access denied".equalsIgnoreCase(operationResult)) {
                    System.out.println("Something went wrong. You weren't register.");
                } else {
                    System.out.println("Something wrong. " + operationResult);
                }
            }

        } while (true);
    }

    private void userAccount(String email) {
        System.out.println("UserAccount");
        String[] userAccountAction = {"Show accounts list", "Create user account", "Delete user account", "Exit"};

        do {
            String choice = Tools.getSelectedMenuItem(userAccountAction);
            if ("Exit".equalsIgnoreCase(choice)) {
                fogOff(email);
                break;
            }
            Account account = new Account(email, server);
            if ("Show accounts list".equalsIgnoreCase(choice)) {
                account.showAccountsList();
            } else if ("Create user account".equalsIgnoreCase(choice)) {
                account.create();
            } else if ("Delete user account".equalsIgnoreCase(choice)) {
                account.delete();
            }
        } while (true);
    }

    private void fogOff(String email) {
        server.logOff(email);
    }
}
