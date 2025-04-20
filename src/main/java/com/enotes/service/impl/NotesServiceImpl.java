package com.enotes.service.impl;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.NotesDto;
import com.enotes.entity.Category;
import com.enotes.entity.Notes;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.NotesRepository;
import com.enotes.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private NotesRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public NotesDto saveNotes(NotesDto dto) {
        CategoryDto category = dto.getCategory();
        Integer id = category.getId();
        Category existCategory = categoryRepository.findById(id).orElseThrow();
        CategoryDto dtoCategory = mapper.map(existCategory, CategoryDto.class);
        dto.setCategory(dtoCategory);
        Notes notes = mapToEntity(dto);
            Notes save = repository.save(notes);
            return mapToDto(save);
    }

    @Override
    public List<NotesDto> getAllNotes() {
        List<Notes> all = repository.findAll();
        List<NotesDto> notesDto = all.stream().map((element) -> mapper.map(element, NotesDto.class)).collect(Collectors.toList());
        return notesDto;
    }

    public NotesDto mapToDto(Notes notes){
       return mapper.map(notes, NotesDto.class);
    }

    public Notes mapToEntity(NotesDto dto){
        return mapper.map(dto, Notes.class);
    }
}
