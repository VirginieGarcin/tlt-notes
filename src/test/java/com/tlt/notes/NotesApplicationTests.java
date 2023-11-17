package com.tlt.notes;

import com.tlt.notes.controller.NoteController;
import com.tlt.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NotesApplicationTests {

    @Autowired
    private NoteController noteController;

    @Autowired
    private NoteService noteService;

    @Test
    void contextLoads() {
        assertNotNull(noteController);
        assertNotNull(noteService);
    }
}