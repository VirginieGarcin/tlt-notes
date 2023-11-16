package com.tlt.notes.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NoteDto extends NoteLightDto {
    private String description;
    private List<String> tags;
}