package com.enotes.controller;

import com.enotes.entity.Category;
import com.enotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saved")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        boolean saved = categoryService.saveCategory(category);
        if (saved){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/create")
    public ResponseEntity<?> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategory();
        if (ObjectUtils.isEmpty(allCategories)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategories, HttpStatus.OK);
        }
    }
}
