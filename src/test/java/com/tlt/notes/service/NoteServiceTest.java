package com.tlt.notes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlt.notes.exception.NoteNotFoundException;
import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    private final NoteRepository noteRepository = mock(NoteRepository.class);
    private final NoteService noteService = new NoteService(noteRepository);

    @Test
    void calculateStats_ReturnsOrderedWordCounts() throws JsonProcessingException {
        var noteRepository = mock(NoteRepository.class);
        var service = new NoteService(noteRepository);

        var description = "note is just note just just";

        var stats = service.calculateStats(description);
        assertEquals("{\"just\":3,\"note\":2,\"is\":1}", new ObjectMapper().writeValueAsString(stats));
    }

    @Test
    void deleteById_ExistingNote_DeletesNote() {
        String noteId = "id1";
        when(noteRepository.existsById(noteId)).thenReturn(true);
        noteService.deleteById(noteId);
        verify(noteRepository).deleteById(noteId);
    }

    @Test
    void deleteById_NonExistingNote_ThrowsNoteNotFoundException() {
        String noteId = "id2";
        when(noteRepository.existsById(noteId)).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(noteId));
    }

    @Test
    void deleteAll_DeletesAllNotes() {
        noteService.deleteAll();
        verify(noteRepository).deleteAll();
    }

    @Test
    void findById_ExistingNote_ReturnsNote() {
        String noteId = "id3";
        var note = new Note();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        assertEquals(note, noteService.findById(noteId));
    }

    @Test
    void findById_NonExistingNote_ThrowsNoteNotFoundException() {
        var noteId = "id4";
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.findById(noteId));
    }

    @Test
    void findAll_NoTagFilter_ReturnsPageOfNotes() {
        var pageable = Pageable.unpaged();
        var mockNotes = new PageImpl<NoteLight>(List.of(
                new Note("id2", "Other note", Instant.ofEpochSecond(200), "", List.of()),
                new Note("id1", "Some note", Instant.ofEpochSecond(100), "", List.of())
        ));
        when(noteRepository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(mockNotes);

        var result = noteService.findAll(pageable, null);

        verify(noteRepository).findAllByOrderByCreatedAtDesc(pageable);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void findAll_WithTagFilter_ReturnsPageOfNotes() {
        var pageable = Pageable.unpaged();
        var tagFilter = "IMPORTANT";
        var mockNotes = new PageImpl<NoteLight>(List.of(
                new Note("id2", "Other note", Instant.ofEpochSecond(200), "", List.of()),
                new Note("id1", "Some note", Instant.ofEpochSecond(100), "", List.of())
        ));
        when(noteRepository.findAllByTagsContainsOrderByCreatedAtDesc(pageable, tagFilter)).thenReturn(mockNotes);

        var result = noteService.findAll(pageable, tagFilter);
        verify(noteRepository).findAllByTagsContainsOrderByCreatedAtDesc(pageable, tagFilter);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void create_ReturnsCreatedNote() {
        var note = new Note();
        when(noteRepository.save(note)).thenReturn(note);

        var result = noteService.create(note);

        verify(noteRepository).save(note);
        assertEquals(note, result);
    }

    @Test
    void update_ExistingNote_ReturnsUpdatedNote() {
        var existingNote = new Note();
        var updatedNote = new Note();
        when(noteRepository.findById(any())).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(updatedNote)).thenReturn(updatedNote);

        var result = noteService.update("id5", updatedNote);

        verify(noteRepository).save(updatedNote);
        assertEquals(updatedNote, result);
    }

    @Test
    void update_NonExistingNote_ThrowsNoteNotFoundException() {
        var updatedNote = new Note();
        when(noteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.update("id6", updatedNote));
    }

    @Test
    void getStats_ExistingNoteWithDescription_ReturnsNoteStatsDto() {
        var note = new Note();
        note.setDescription("Some note");
        when(noteRepository.findById(any())).thenReturn(Optional.of(note));

        assertFalse(noteService.getStats("id7").isEmpty());
    }

    @Test
    void getStats_ExistingNoteWithoutDescription_ReturnsEmptyNoteStatsDto() {
        var note = new Note();
        when(noteRepository.findById(any())).thenReturn(Optional.of(note));

        assertTrue(noteService.getStats("id7").isEmpty());
    }

    @Test
    void getStats_NonExistingNote_ThrowsNoteNotFoundException() {
        when(noteRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoteNotFoundException.class, () -> noteService.getStats("id8"));
    }
}