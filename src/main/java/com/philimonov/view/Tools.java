package com.philimonov.view;

import java.util.Scanner;

public class Tools {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getSelectedMenuItem(String[] menu) {
        if (menu == null || menu.length == 0) {
            return null;
        }
        int value;
        do {
            System.out.println("Выберите операцию: ");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ". " + menu[i]);
            }
            System.out.println("Ваш выбор: ");
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0 && value < menu.length) {
                    break;
                } else {
                    System.out.println("Неверные данные!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный выбор.");
            }
        } while (true);
        return menu[value - 1];
    }

    public static String getEmail() {
        String exit = "q";
        String input;
        boolean isEmailValid;
        do{
            System.out.println("Введите email или EXIT: ");
            input = getNewLine();
            if (input.equalsIgnoreCase(exit)) {
                return null;
            }
            isEmailValid = checkEmail(input);
            if (!isEmailValid) {
                System.out.println("Неверные данные.\n Попробуйте ещё раз.");
            }
        }while (!isEmailValid);
        return input;
    }

    public static String getPassword() {
        String exit = "q";
        String input;
        boolean isPasswordValid;
        do {
            System.out.println("Введите пароль (не менее 6 символов) или нажмите q для выхода: ");
            input = getNewLine();
            if (input.equalsIgnoreCase(exit)) {
                return null;
            }
            isPasswordValid = checkPassword(input);
            if (!isPasswordValid) {
                System.out.println("Неверные данные.\n Попробуйте ещё раз.");
            }
        }while(!isPasswordValid);
        return input;
    }

    private static boolean checkEmail(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    private static boolean checkPassword(String password) {
        return password.length() > 5;
    }

    public static String getNewLine() {
        return scanner.nextLine();
    }

    public static long getLongValue() {
        while (true){
            try{
                return Long.parseLong(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Некорректный ввод. Повторите заново: ");
            }
        }
    }

    public static int getIntValue() {
        while (true){
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Некорректный ввод. Повторите заново: ");
            }
        }
    }
}
