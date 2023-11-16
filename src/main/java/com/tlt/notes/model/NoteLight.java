package com.tlt.notes.model;

import java.time.Instant;

public interface NoteLight {
    String getId();

    String getTitle();

    Instant getCreatedAt();
}
