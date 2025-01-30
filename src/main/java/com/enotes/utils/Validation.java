package com.enotes.utils;

import com.enotes.dto.CategoryDto;
import com.enotes.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto){

        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("Category object shouldn't be null or empty");
        } else {
            //name validation
            if(ObjectUtils.isEmpty(categoryDto.getName())){
//                throw new IllegalArgumentException("Category name is null or empty");
                error.put("name", "name field is empty or null");
            } else {
                if(ObjectUtils.isEmpty(categoryDto.getName().length() < 10)){
                    error.put("name", "name length is lesser than 10.");
                }
                if (ObjectUtils.isEmpty(categoryDto.getName().length() > 100)){
                    error.put("name", "name length is greater than 100.");
                }
            }
            //description validation
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description", "description field is empty or null");
            }

            //active status validation
            if (ObjectUtils.isEmpty(categoryDto.isActive())){
                error.put("active", "isActive field is empty");
            } else {
                if (categoryDto.isActive() != Boolean.TRUE.booleanValue() && categoryDto.isActive() != Boolean.FALSE.booleanValue()){
                    error.put("active", "invalid value");
                }
            }
        }
        if (!error.isEmpty()){
            throw new ValidationException(error);
        }
    }
}
