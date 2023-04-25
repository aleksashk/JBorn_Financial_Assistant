package com.philimonov.service;

import com.philimonov.converter.CategoryModelToCategoryDtoConverter;
import com.philimonov.converter.ReportCategoryModelToReportCategoryDtoConverter;
import com.philimonov.dao.CategoryDao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;
    private final ReportCategoryModelToReportCategoryDtoConverter reportCategoryDtoConverter;

    public CategoryService(CategoryDao categoryDao, CategoryModelToCategoryDtoConverter categoryDtoConverter, ReportCategoryModelToReportCategoryDtoConverter reportCategoryDtoConverter) {
        this.categoryDao = categoryDao;
        this.categoryDtoConverter = categoryDtoConverter;
        this.reportCategoryDtoConverter = reportCategoryDtoConverter;
    }

    public List<CategoryDTO> findAllByPersonId(int personId) {
        return categoryDao.findAllByPersonId(personId).stream().map(categoryDtoConverter::convert).collect(toList());
    }

    public CategoryDTO insert(String name, int personId) {
        return Optional.ofNullable(categoryDao.insert(name, personId))
                .map(categoryDtoConverter::convert)
                .orElse(null);
    }

    public CategoryDTO update(String name, int id, int personId) {
        return Optional.ofNullable(categoryDao.update(name, id, personId)).map(categoryDtoConverter::convert).orElse(null);
    }

    public boolean delete(int id, int personId) {
        return true;
    }
    public List<ReportCategoryDTO> getIncomeReportByCategory(Date from, Date to, int personId){
        return categoryDao.getIncomeReportByCategory(from, to, personId).stream().map(reportCategoryDtoConverter::convert).collect(toList());
    }
    public List<ReportCategoryDTO>getExpenseReportByCategory(Date from, Date to, int personId){
        return categoryDao.getExpenseReportByCategory(from, to, personId).stream().map(reportCategoryDtoConverter::convert).collect(toList());
    }
}
