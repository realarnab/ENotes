package com.enotes.service.impl;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);

        if (ObjectUtils.isEmpty(category.getId())){
            category.setDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        } else {
            updateCategory(category);
        }

        Category saved = categoryRepository.save(category);

        if (ObjectUtils.isEmpty(saved)) {
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> byId = categoryRepository.findById(category.getId());

        if (byId.isPresent()){
            Category existCategory = byId.get();
            category.setCreatedBy(existCategory.getCreatedBy());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setDeleted(existCategory.isDeleted());
            category.setUpdateBy(1);
            category.setUpdatedOn(new Date());
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        return categories.stream().map(e -> modelMapper.map(e, CategoryDto.class)).toList();
    }

    @Override
    public List<CategoryResponse> getAllActiveCategory() {
        List<Category> all = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return all.stream().map(e -> modelMapper.map(e, CategoryResponse.class)).toList();
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        Category byId = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found with Id: "+id));
        return mapToDto(byId);
    }

    @Override
    public boolean deleteCategoryById(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()){
            Category category = byId.get();
            category.setDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public Category mapToEntity(CategoryDto dto){
        return modelMapper.map(dto, Category.class);
    }

    public CategoryDto mapToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}
