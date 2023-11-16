package com.tlt.notes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NoteDto extends NoteLightDto {
    @NotBlank(message = "Description can't be empty")
    private String description;
    private List<String> tags;
}