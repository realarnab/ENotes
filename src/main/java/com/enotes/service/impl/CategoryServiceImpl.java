package com.enotes.service.impl;

import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean saveCategory(Category category) {
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
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
}
