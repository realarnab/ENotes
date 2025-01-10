package com.enotes.service;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    public boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getAllActiveCategory();

    CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;

    boolean deleteCategoryById(Integer id);
}
