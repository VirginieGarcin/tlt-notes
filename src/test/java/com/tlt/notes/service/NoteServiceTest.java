package com.tlt.notes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlt.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class NoteServiceTest {
    @Test
    void calculateStats_ReturnsOrderedWordCounts() throws JsonProcessingException {
        var noteRepository = mock(NoteRepository.class);
        var service = new NoteService(noteRepository);

        var description = "note is just note just just";

        var stats = service.calculateStats(description);
        assertEquals("{\"just\":3,\"note\":2,\"is\":1}", new ObjectMapper().writeValueAsString(stats));
    }
}