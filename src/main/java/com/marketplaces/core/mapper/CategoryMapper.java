package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.create.CategoryCreateRequestDTO;
import com.marketplaces.core.dto.response.CategoryResponseDTO;
import com.marketplaces.core.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryMapper {

    public Category convert(CategoryCreateRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setFullName(dto.getFullName());
        return category;
    }

    public CategoryResponseDTO convert(Category category) {
        CategoryResponseDTO categoryResponseDTO = convertWithoutParent(category);
        if(category.getParent() != null) {
            categoryResponseDTO.setParent(this.convert(category.getParent()));
        }
        return categoryResponseDTO;
    }

    public List<CategoryResponseDTO> convertToList(Category category) {
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        categoryResponseDTOList.add(convertWithoutParent(category));
        var tmp = category.getParent();
        while (tmp != null) {
            categoryResponseDTOList.add(convertWithoutParent(tmp));
            tmp = tmp.getParent();
        }
        return categoryResponseDTOList;
    }

    public CategoryResponseDTO convertWithoutParent(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setFullName(category.getFullName());
        if (category.getParent() != null) {
            categoryResponseDTO.setParentId(category.getParent().getId());
        }
        return categoryResponseDTO;
    }

}
