package com.philimonov.service;

import com.philimonov.converter.CategoryModelToDtoConverter;
import com.philimonov.dao.CategoryDao;
import com.philimonov.dao.CategoryModel;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryModelToDtoConverter categoryModelToDtoConverter;

    public CategoryService() {
        categoryDao = new CategoryDao();
        categoryModelToDtoConverter = new CategoryModelToDtoConverter();
    }

    public List<CategoryDTO> getAllByUserId(long userId) {
        return categoryDao.getAllByUserId(userId)
                .stream()
                .map(categoryModelToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public CategoryDTO insert(String name, long userId) {
        if (categoryDao.findByNameAndUserId(name, userId) == null) {
            CategoryModel categoryModel = categoryDao.create(name, userId);
            return categoryModelToDtoConverter.convert(categoryModel);
        }
        return null;
    }

    public CategoryDTO update(long id, String newName, long userId) {
        if (categoryDao.findByNameAndUserId(newName, userId) == null) {
            CategoryModel categoryModel = categoryDao.update(id, newName, userId);
            return categoryModelToDtoConverter.convert(categoryModel);
        }
        return null;
    }

    public void delete(long id, long userId) {
        categoryDao.delete(id, userId);
    }
}
