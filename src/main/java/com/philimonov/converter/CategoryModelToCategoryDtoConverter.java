package com.philimonov.converter;

import com.philimonov.dao.CategoryModel;
import com.philimonov.service.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoryModelToCategoryDtoConverter implements Converter<CategoryModel, CategoryDTO> {
    @Override
    public CategoryDTO convert(CategoryModel source) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(source.getId());
        categoryDTO.setName(source.getName());
        return categoryDTO;
    }
}
