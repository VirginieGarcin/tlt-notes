package com.tlt.notes.controller;

import com.tlt.notes.model.Note;
import com.tlt.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NoteControllerTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void getAllNotes_ReturnsListOfNotes_WhenServiceReturnsNotes() {
        var noteService = mock(NoteService.class);
        var mockNotes = List.of(
                new Note("id2", "Other note", Instant.ofEpochSecond(200), "", List.of()),
                new Note("id1", "Some note", Instant.ofEpochSecond(100), "", List.of())
        );
        when(noteService.findAll()).thenReturn(mockNotes);

        var notes = new NoteController(noteService, modelMapper).getAllNotes();
        assertEquals(2, notes.size());
    }
}