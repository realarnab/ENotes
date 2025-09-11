package com.enotes.controller;

import com.enotes.dto.NotesDto;
import com.enotes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/saved")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile multipartFile) throws IOException {
        NotesDto notesDto = notesService.saveNotes(notes, multipartFile);
        return new ResponseEntity<>(notesDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        return new ResponseEntity<>(allNotes, HttpStatus.OK);
    }
}
