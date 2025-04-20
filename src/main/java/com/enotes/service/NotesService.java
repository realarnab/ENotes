package com.enotes.service;

import com.enotes.dto.NotesDto;

import java.util.List;

public interface NotesService {
    public NotesDto saveNotes(NotesDto dto);
    public List<NotesDto> getAllNotes();
}
