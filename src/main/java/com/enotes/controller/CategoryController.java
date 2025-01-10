package com.enotes.controller;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
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
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        boolean saved = categoryService.saveCategory(categoryDto);
        if (saved){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategory();
        if (ObjectUtils.isEmpty(allCategories)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategories, HttpStatus.OK);
        }
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allActiveCategory = categoryService.getAllActiveCategory();
        if (ObjectUtils.isEmpty(allActiveCategory)){
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allActiveCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id){
        CategoryDto category = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(category)){
            return new ResponseEntity<>("Category not found with Id: "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
      boolean status = categoryService.deleteCategoryById(id);
      if (status){
          return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
      }
      return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
