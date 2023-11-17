package com.tlt.notes.controller;

import com.tlt.notes.dto.NoteDto;
import com.tlt.notes.dto.NoteStatsDto;
import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    private final ModelMapper modelMapper = new ModelMapper();
    private final NoteService noteService = mock(NoteService.class);
    private final NoteController noteController = new NoteController(noteService, modelMapper);

    @Test
    void getNotesList_ReturnsPageOfNotes() {
        var mockNotes = new PageImpl<NoteLight>(List.of(
                new Note("id2", "Other note", Instant.ofEpochSecond(200), "", List.of()),
                new Note("id1", "Some note", Instant.ofEpochSecond(100), "", List.of())
        ));

        when(noteService.findAll(PageRequest.of(0, 5), null)).thenReturn(mockNotes);

        var notes = noteController.getNotesList(PageRequest.of(0, 5), null);
        assertEquals(2, notes.getContent().size());
    }

    @Test
    void getNoteById_ReturnsNoteDto() {
        var id = "id3";
        var note = new Note(id, "Some note", Instant.ofEpochSecond(100), "", List.of());

        when(noteService.findById(id)).thenReturn(note);

        var resultNote = noteController.getNoteById(id);

        assertNotNull(resultNote);
        assertEquals(note.getId(), resultNote.getId());
    }

    @Test
    void getNoteStats_ReturnsNoteStatsDto() {
        var noteId = "id4";
        when(noteService.getStats(noteId)).thenReturn(new NoteStatsDto());
        assertNotNull(noteController.getNoteStats(noteId));
    }

    @Test
    void createNote_ReturnsCreatedNoteDto() {
        var noteId = "id5";
        when(noteService.create(any())).thenReturn(new Note(noteId, "Some note", Instant.ofEpochSecond(100), "", List.of()));

        var result = noteController.createNote(new NoteDto());

        verify(noteService).create(any());
        assertEquals(noteId, result.getId());
    }

    @Test
    void updateNote_ReturnsUpdatedNoteDto() {
        var noteId = "id6";
        when(noteService.update(eq(noteId), any())).thenReturn(new Note(noteId, "Some note", Instant.ofEpochSecond(100), "", List.of()));

        var result = noteController.updateNote(noteId, new NoteDto());

        verify(noteService).update(eq(noteId), any());
        assertEquals(noteId, result.getId());
    }

    @Test
    void deleteNote_DeletesNoteById() {
        var noteId = "id7";
        noteController.deleteNote(noteId);
        verify(noteService).deleteById(noteId);
    }

    @Test
    void deleteAll_DeletesAllNotes() {
        noteController.deleteNote();
        verify(noteService).deleteAll();
    }
}