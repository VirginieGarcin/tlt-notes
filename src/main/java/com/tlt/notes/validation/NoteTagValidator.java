package com.tlt.notes.validation;

import com.tlt.notes.model.NoteTag;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class NoteTagValidator implements ConstraintValidator<ValidNoteTag, String> {
    public boolean isValid(String noteTagName, ConstraintValidatorContext cxt) {
        return Arrays.stream(NoteTag.values()).map(Enum::toString).toList().contains(noteTagName);
    }
}