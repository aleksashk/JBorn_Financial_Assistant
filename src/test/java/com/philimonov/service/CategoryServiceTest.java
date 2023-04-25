package com.philimonov.service;

import com.philimonov.converter.CategoryModelToCategoryDtoConverter;
import com.philimonov.converter.ReportCategoryModelToReportCategoryDtoConverter;
import com.philimonov.dao.CategoryDao;
import com.philimonov.dao.CategoryModel;
import com.philimonov.dao.ReportCategoryModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    CategoryService service;
    @Mock
    CategoryDao categoryDao;
    @Mock
    CategoryModelToCategoryDtoConverter categoryDtoConverter;
    @Mock
    ReportCategoryModelToReportCategoryDtoConverter reportCategoryDtoConverter;

    int id = 1;
    String name = "transaction 1";
    int personId = 1;

    @Test
    public void findAllByPersonIdSuccess() {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        int numberCategories = 3;

        for (int i = 0; i < numberCategories; i++) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(id);
            categoryModel.setName(name);
            categoryModel.setPersonId(personId);
            categoryModelList.add(categoryModel);

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(id);
            categoryDTO.setName(name);
            categoryDTOList.add(categoryDTO);

            when(categoryDtoConverter.convert(categoryModelList.get(i))).thenReturn(categoryDTOList.get(i));
        }
        when(categoryDao.findAllByPersonId(personId)).thenReturn(categoryModelList);
        List<CategoryDTO> categoryList = service.findAllByPersonId(personId);
        assertFalse(categoryList.isEmpty());
        assertEquals(categoryList, categoryDTOList);

        verify(categoryDao, times(1)).findAllByPersonId(personId);
        verify(categoryDtoConverter, times(numberCategories)).convert(anyObject());
    }

    @Test
    public void findAllByPersonIdFailed() {
        List<CategoryModel> categoryModelList = new ArrayList<>();

        when(categoryDao.findAllByPersonId(personId)).thenReturn(categoryModelList);
        List<CategoryDTO> categoryList = service.findAllByPersonId(personId);
        assertFalse(categoryList.isEmpty());

        verify(categoryDao, times(1)).findAllByPersonId(personId);
        verifyZeroInteractions(categoryDtoConverter);
    }

    @Test
    public void insert() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setName(name);
        categoryModel.setPersonId(personId);
        when(categoryDao.insert(name, personId)).thenReturn(categoryModel);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setName(name);
        when(categoryDtoConverter.convert(categoryModel)).thenReturn(categoryDTO);

        CategoryDTO category = service.insert(name, personId);
        assertNotNull(category);
        assertEquals(categoryDTO, category);
        verify(categoryDao, times(1)).insert(name, personId);
        verify(categoryDtoConverter, times(1)).convert(categoryModel);
    }

    @Test
    public void update() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setName(name);
        categoryModel.setPersonId(personId);
        when(categoryDao.update(name, id, personId)).thenReturn(categoryModel);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setName(name);
        when(categoryDtoConverter.convert(categoryModel)).thenReturn(categoryDTO);

        CategoryDTO category = service.update(name, id, personId);
        assertNotNull(category);
        assertEquals(categoryDTO, category);
        verify(categoryDao, times(1)).update(name, id, personId);
        verify(categoryDtoConverter, times(1)).convert(categoryModel);
    }

    @Test
    public void delete() {
        when(categoryDao.delete(id, personId)).thenReturn(true);
        assertTrue(service.delete(id, personId));
        verify(categoryDao, times(1)).delete(id, personId);
    }

    @Test
    public void getIncomeReportByCategory() throws ParseException {
        List<ReportCategoryModel> reportModelList = new ArrayList<>();
        List<ReportCategoryDTO> reportDTOList = new ArrayList<>();
        int numberReports = 3;
        long amount = 50000;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date from = formatter.parse("01-01-2022");
        Date to = formatter.parse("01-01-2023");

        for (int i = 0; i < numberReports; i++) {
            ReportCategoryModel reportModel = new ReportCategoryModel();
            reportModel.setName(name);
            reportModel.setAmount(amount);
            reportModelList.add(reportModel);

            ReportCategoryDTO reportDTO = new ReportCategoryDTO();
            reportDTO.setName(name);
            reportDTO.setAmount(amount);
            reportDTOList.add(reportDTO);

            when(reportCategoryDtoConverter.convert(reportModelList.get(i))).thenReturn(reportDTOList.get(i));
        }
        when(categoryDao.getIncomeReportByCategory(from, to, personId)).thenReturn(reportModelList);

        List<ReportCategoryDTO> reportList = service.getIncomeReportByCategory(from, to, personId);
        assertFalse(reportList.isEmpty());
        assertEquals(reportList, reportDTOList);

        verify(categoryDao, times(1)).getIncomeReportByCategory(from, to, personId);
        verify(reportCategoryDtoConverter, times(numberReports)).convert(anyObject());
    }

    @Test
    public void getExpenseReportByCategory() throws ParseException {
        List<ReportCategoryModel> reportModelList = new ArrayList<>();
        List<ReportCategoryDTO> reportDTOList = new ArrayList<>();
        int numberReports = 3;
        long amount = 50000;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date from = formatter.parse("01-01-2022");
        Date to = formatter.parse("01-01-2023");

        for (int i = 0; i < numberReports; i++) {
            ReportCategoryModel reportModel = new ReportCategoryModel();
            reportModel.setName(name);
            reportModel.setAmount(amount);
            reportModelList.add(reportModel);

            ReportCategoryDTO reportDTO = new ReportCategoryDTO();
            reportDTO.setName(name);
            reportDTO.setAmount(amount);
            reportDTOList.add(reportDTO);

            when(reportCategoryDtoConverter.convert(reportModelList.get(i))).thenReturn(reportDTOList.get(i));
        }
        when(categoryDao.getExpenseReportByCategory(from, to, personId)).thenReturn(reportModelList);

        List<ReportCategoryDTO> reportList = service.getExpenseReportByCategory(from, to, personId);
        assertFalse(reportList.isEmpty());
        assertEquals(reportList, reportDTOList);

        verify(categoryDao, times(1)).getExpenseReportByCategory(from, to, personId);
        verify(reportCategoryDtoConverter, times(numberReports)).convert(anyObject());
    }
}