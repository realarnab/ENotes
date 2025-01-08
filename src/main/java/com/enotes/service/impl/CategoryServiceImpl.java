package com.enotes.service.impl;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        category.setDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saved = categoryRepository.save(category);

        if (ObjectUtils.isEmpty(saved)) {
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(e -> modelMapper.map(e, CategoryDto.class)).toList();
    }

    @Override
    public List<CategoryResponse> getAllActiveCategory() {
        List<Category> all = categoryRepository.findByIsActiveTrue();
        return all.stream().map(e -> modelMapper.map(e, CategoryResponse.class)).toList();
    }

    public Category mapToEntity(CategoryDto dto){
        return modelMapper.map(dto, Category.class);
    }

    public CategoryDto mapToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}
