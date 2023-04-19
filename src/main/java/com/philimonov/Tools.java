package com.philimonov;

import java.util.Scanner;

public class Tools {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getSelectedMenuItem(String[] menu) {
        if (menu == null || menu.length == 0) {
            return null;
        }
        int value;
        do {
            System.out.println("Select an operation: ");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ". " + menu[i]);
            }
            System.out.println("Your choice is: ");
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0 && value < menu.length) {
                    break;
                } else {
                    System.out.println("Wrong data!!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
            }
        } while (true);
        return menu[value - 1];
    }

    public static String getEmail() {
        String exit = "q";
        String input;
        boolean isEmailValid;
        do{
            System.out.println("Input an email or press q for EXIT: ");
            input = getNewLine();
            if (input.equalsIgnoreCase(exit)) {
                return null;
            }
            isEmailValid = checkEmail(input);
            if (!isEmailValid) {
                System.out.println("Invalid data!\n Try again.");
            }
        }while (!isEmailValid);
        return input;
    }

    public static String getPassword() {
        String exit = "q";
        String input;
        boolean isPasswordValid;
        do {
            System.out.println("Input a password (more than 10 characters) or press q for EXIT: ");
            input = getNewLine();
            if (input.equalsIgnoreCase(exit)) {
                return null;
            }
            isPasswordValid = checkPassword(input);
            if (!isPasswordValid) {
                System.out.println("Invalid data!\n Try again.");
            }
        }while(!isPasswordValid);
        return input;
    }

    private static boolean checkEmail(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    private static boolean checkPassword(String password) {
        return password.length() > 10;
    }

    public static String getNewLine() {
        return scanner.nextLine();
    }
}
