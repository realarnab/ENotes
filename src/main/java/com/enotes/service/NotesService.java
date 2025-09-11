package com.enotes.service;

import com.enotes.dto.NotesDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {
    public NotesDto saveNotes(String notes, MultipartFile file) throws IOException;
    public List<NotesDto> getAllNotes();
}
