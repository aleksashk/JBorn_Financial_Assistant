package com.philimonov.converter;

import com.philimonov.dao.CategoryModel;
import com.philimonov.service.CategoryDto;

public class CategoryModelToCategoryDtoConverter implements Converter<CategoryModel, CategoryDto> {
    @Override
    public CategoryDto convert(CategoryModel source) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(source.getId());
        categoryDto.setName(source.getName());
        return categoryDto;
    }
}
