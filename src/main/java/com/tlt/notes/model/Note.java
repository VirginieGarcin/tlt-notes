package com.tlt.notes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "notes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Note {
    @Id
    private String id;
    private String title;
    private Instant createdAt;
    private String description;
    private List<NoteTag> tags;
}
