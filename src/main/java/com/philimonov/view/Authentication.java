package com.philimonov.view;

import com.philimonov.service.AuthService;
import com.philimonov.service.PersonDTO;

import java.util.Optional;

import static com.philimonov.FinancialAssistant.getAuthService;

public class Authentication {
    private final AuthService authService;

    public Authentication() {
        this.authService = getAuthService();
    }

    public void showMenu() {
        String[] mainMenu = { "Войти в личный кабинет", "Зарегистрироваться", "Выйти" };
        while (true) {
            String choice = Tools.getSelectedMenuItem(mainMenu);
            if ("Войти в личный кабинет".equals(choice)) {
                logIn();
            } else if ("Зарегистрироваться".equals(choice)) {
                logUp();
            } else if ("Выйти".equals(choice)) {
                break;
            }
        }
    }

    private void logIn() {
        System.out.println("Вход в личный кабинет.");
        String email = Tools.getEmail();
        if (email == null) {
            return;
        }
        String password = Tools.getPassword();
        Optional<PersonDTO> person = Optional.ofNullable(authService.auth(email, password));
        if (person.isPresent()) {
            new PersonalCabinet(person.get()).showMenu();
        } else {
            System.out.println("Авторизация не выполнена. Вы не зарегистрированы.");
        }
    }

    private void logUp() {
        System.out.println("Регистрация.");
        String email = Tools.getEmail();
        if (email == null) {
            return;
        }
        String password = Tools.getPassword();
        Optional<PersonDTO> person = Optional.ofNullable(authService.registration(email, password));
        if (person.isPresent()) {
            System.out.println("Регистрация выполнена успешно.");
        } else {
            System.out.println("При регистрации возникли проблемы. Регистрация не была выполнена.");
        }
    }
}
