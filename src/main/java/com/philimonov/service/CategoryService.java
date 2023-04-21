package com.philimonov.service;

import com.philimonov.converter.CategoryModelToCategoryDtoConverter;
import com.philimonov.dao.CategoryDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryService() {
        this.categoryDao = new CategoryDao();
        this.categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
    }

    public List<CategoryDTO> findAllByPersonId(int personId) {
        return categoryDao.findAllByPersonId(personId).stream().map(categoryDtoConverter::convert).collect(Collectors.toList());
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
}
