package com.philimonov.service;

import static com.philimonov.converter.ConverterFactory.getAccountDtoConverter;
import static com.philimonov.converter.ConverterFactory.getCategoryDtoConverter;
import static com.philimonov.converter.ConverterFactory.getPersonDtoConverter;
import static com.philimonov.converter.ConverterFactory.getReportCategoryDtoConverter;
import static com.philimonov.dao.DaoFactory.getAccountDao;
import static com.philimonov.dao.DaoFactory.getCategoryDao;
import static com.philimonov.dao.DaoFactory.getPersonDao;

public class ServiceFactory {
    private static AuthService authService;
    private static AccountService accountService;
    private static CategoryService categoryService;

    public static AuthService getAuthService() {
        if(authService == null){
            authService = new AuthService(getPersonDao(), new Md5DigestService(), getPersonDtoConverter());
        }
        return authService;
    }

    public static AccountService getAccountService() {
        if(accountService == null){
            accountService = new AccountService(getAccountDao(), getAccountDtoConverter());
        }
        return accountService;
    }

    public static CategoryService getCategoryService() {
        if(categoryService == null){
            categoryService = new CategoryService(getCategoryDao(), getCategoryDtoConverter(), getReportCategoryDtoConverter());
        }
        return categoryService;
    }
}
