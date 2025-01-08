package com.enotes.service;

import com.enotes.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    public boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}
