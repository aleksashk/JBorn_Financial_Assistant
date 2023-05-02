package com.philimonov;

import com.philimonov.service.AccountService;
import com.philimonov.service.AuthService;
import com.philimonov.service.CategoryService;
import com.philimonov.service.PersonDTO;
import com.philimonov.service.TransactionService;
import com.philimonov.view.Authentication;
import com.philimonov.view.PersonalCabinet;
import com.philimonov.view.Tools;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class FinancialAssistant {
    private static final ApplicationContext context =
            new AnnotationConfigApplicationContext("com.korneev");

    public static AuthService getAuthService() {
        return context.getBean(AuthService.class);
    }

    public static AccountService getAccountService() {
        return context.getBean(AccountService.class);
    }

    public static CategoryService getCategoryService() {
        return context.getBean(CategoryService.class);
    }

    public static TransactionService getTransactionService() {
        return context.getBean(TransactionService.class);
    }

    public static void main(String[] args) {
        new Authentication().showMenu();
    }
}