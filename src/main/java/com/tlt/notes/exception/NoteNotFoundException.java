package com.tlt.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
        super("Note not found");
    }
}