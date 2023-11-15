package com.tlt.notes.dto;

import lombok.*;
import org.springframework.format.datetime.standard.InstantFormatter;

import java.text.ParseException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NoteDto {
    private static final InstantFormatter instantFormat = new InstantFormatter();
    private String id;
    private String title;
    private String createdAt;
    private String description;
    private List<String> tags;

    public Instant getCreatedAt() {
        try {
            return Instant.from(instantFormat.parse(createdAt, Locale.ENGLISH));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCreatedAt(Instant instant) {
        createdAt = instantFormat.print(instant, Locale.ENGLISH);
    }
}