package com.tlt.notes.dto;

import lombok.*;
import org.springframework.format.datetime.standard.InstantFormatter;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NoteLightDto {
    private static final InstantFormatter instantFormat = new InstantFormatter();
    private String id;
    private String title;
    private String createdAt;

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