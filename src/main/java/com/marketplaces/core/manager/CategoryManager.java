package com.marketplaces.core.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplaces.core.dto.create.CategoryCreateRequestDTO;
import com.marketplaces.core.dto.response.CategoryResponseDTO;
import com.marketplaces.core.entity.Category;
import com.marketplaces.core.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryManager {

    private final CategoryService categoryService;
    private final ObjectMapper mapper;

    public CategoryManager(
            CategoryService categoryService,
            ObjectMapper mapper
    ) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    public CategoryResponseDTO create(CategoryCreateRequestDTO categoryCreateRequestDTO) {
        Category category = new Category();
        category.setName(categoryCreateRequestDTO.getName());
        category.setFullName(categoryCreateRequestDTO.getFullName());

        if (categoryCreateRequestDTO.getParentId() !=null) {
            var parent = categoryService.findById(categoryCreateRequestDTO.getParentId());
            category.setParent(parent);
        }

        categoryService.save(category);

        return mapper.convertValue(category, CategoryResponseDTO.class);
    }
}
