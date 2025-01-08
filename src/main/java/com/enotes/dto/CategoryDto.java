package com.enotes.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    
    private Integer id;
    private String name;
    private String description;
    private boolean isActive;
    private boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updateBy;
    private Date updatedOn;
}
