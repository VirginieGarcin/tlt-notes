package com.tlt.notes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NoteTagValidator.class)
public @interface ValidNoteTag {
    String message() default "Invalid tag";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}