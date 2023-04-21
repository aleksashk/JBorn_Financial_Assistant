package com.philimonov.converter;

import com.philimonov.dao.CategoryModel;
import com.philimonov.service.CategoryDTO;

public class CategoryModelToCategoryDtoConverter implements Converter<CategoryModel, CategoryDTO> {
    @Override
    public CategoryDTO convert(CategoryModel source) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(source.getId());
        categoryDto.setName(source.getName());
        return categoryDto;
    }
}
