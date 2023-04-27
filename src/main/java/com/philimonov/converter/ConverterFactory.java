package com.philimonov.converter;

import com.philimonov.dao.AccountModel;
import com.philimonov.dao.CategoryModel;
import com.philimonov.dao.PersonModel;
import com.philimonov.dao.ReportCategoryModel;
import com.philimonov.dao.TransactionModel;
import com.philimonov.service.AccountDTO;
import com.philimonov.service.CategoryDTO;
import com.philimonov.service.PersonDTO;
import com.philimonov.service.ReportCategoryDTO;
import com.philimonov.service.TransactionDTO;

public class ConverterFactory {
    private static Converter<PersonModel, PersonDTO> personDtoConverter;
    private static Converter<AccountModel, AccountDTO> accountDtoConverter;
    private static Converter<CategoryModel, CategoryDTO> categoryDtoConverter;
    private static Converter<TransactionModel, TransactionDTO> transactionDtoConverter;
    private static Converter<ReportCategoryModel, ReportCategoryDTO> reportCategoryDtoConverter;

    public static Converter<PersonModel, PersonDTO> getPersonDtoConverter() {
        if (personDtoConverter == null) {
            personDtoConverter = new PersonModelToPersonDtoConverter();
        }
        return personDtoConverter;
    }

    public static Converter<AccountModel, AccountDTO> getAccountDtoConverter() {
        if (accountDtoConverter == null) {
            accountDtoConverter = new AccountModelToAccountDtoConverter();
        }
        return accountDtoConverter;
    }

    public static Converter<CategoryModel, CategoryDTO> getCategoryDtoConverter() {
        if (categoryDtoConverter == null) {
            categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
        }
        return categoryDtoConverter;
    }

    public static Converter<ReportCategoryModel, ReportCategoryDTO> getReportCategoryDtoConverter() {
        if (reportCategoryDtoConverter == null) {
            reportCategoryDtoConverter = new ReportCategoryModelToReportCategoryDTOConverter();
        }
        return reportCategoryDtoConverter;
    }

    public static Converter<TransactionModel, TransactionDTO> getTransactionDtoConverter() {
        if (transactionDtoConverter == null) {
            transactionDtoConverter = new TransactionModelToTransactionDtoConverter();
        }
        return transactionDtoConverter;
    }
}
