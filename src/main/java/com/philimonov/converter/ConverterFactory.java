package com.philimonov.converter;

public class ConverterFactory {
    private static PersonModelToPersonDtoConverter personDtoConverter;
    private static AccountModelToAccountDtoConverter accountDtoConverter;
    private static CategoryModelToCategoryDtoConverter categoryDtoConverter;
    private static ReportCategoryModelToReportCategoryDtoConverter reportCategoryDtoConverter;

    public static PersonModelToPersonDtoConverter getPersonDtoConverter() {
        if(personDtoConverter == null){
            personDtoConverter = new PersonModelToPersonDtoConverter();
        }
        return personDtoConverter;
    }

    public static AccountModelToAccountDtoConverter getAccountDtoConverter() {
        if(accountDtoConverter == null){
            accountDtoConverter = new AccountModelToAccountDtoConverter();
        }
        return accountDtoConverter;
    }

    public static CategoryModelToCategoryDtoConverter getCategoryDtoConverter() {
        if(categoryDtoConverter==null){
            categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
        }
        return categoryDtoConverter;
    }

    public static ReportCategoryModelToReportCategoryDtoConverter getReportCategoryDtoConverter() {
        if(reportCategoryDtoConverter == null){
            reportCategoryDtoConverter = new ReportCategoryModelToReportCategoryDtoConverter();
        }
        return reportCategoryDtoConverter;
    }
}
