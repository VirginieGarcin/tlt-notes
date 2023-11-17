package com.tlt.notes.validation;

import com.tlt.notes.model.NoteTag;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class NoteTagValidatorTest {
    private final NoteTagValidator validator = new NoteTagValidator();
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

    @Test
    void isValid_ValidNoteTagName_ReturnsTrue() {
        assertTrue(validator.isValid(NoteTag.IMPORTANT.toString(), context));
    }

    @Test
    void isValid_InvalidNoteTagName_ReturnsFalse() {
        assertFalse(validator.isValid("InvalidTag", context));
    }

    @Test
    void isValid_NullNoteTagName_ReturnsFalse() {
        assertFalse(validator.isValid(null, context));
    }
}