package com.enotes.controller;

import com.enotes.dto.NotesDto;
import com.enotes.entity.Category;
import com.enotes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/saved")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto dto){
        NotesDto notesDto = notesService.saveNotes(dto);
        return new ResponseEntity<>(notesDto, HttpStatus.CREATED);
    }
}
