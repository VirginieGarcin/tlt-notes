package com.tlt.notes.service;

import com.tlt.notes.dto.NoteStatsDto;
import com.tlt.notes.exception.NoteNotFoundException;
import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.repository.NoteRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {
    final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void deleteById(String id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new NoteNotFoundException();
        }
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }

    public @NotNull Note findById(String id) {
        return noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    public Page<NoteLight> findAll(Pageable pageable, String tagFilter) {
        if (tagFilter == null) {
            return noteRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            return noteRepository.findAllByTagsContainsOrderByCreatedAtDesc(pageable, tagFilter);
        }
    }

    public @NotNull Note create(Note note) {
        return noteRepository.save(note);
    }

    public @NotNull Note update(String id, Note note) {
        var initialNote = findById(id);
        note.setId(initialNote.getId());
        return noteRepository.save(note);
    }

    public @NotNull NoteStatsDto getStats(String id) {
        var note = findById(id);
        if (StringUtils.hasText(note.getDescription())) {
            return calculateStats(note.getDescription());
        } else {
            return new NoteStatsDto();
        }
    }

    NoteStatsDto calculateStats(@NotBlank String description) {
        var stats = new NoteStatsDto();
        var allWords = Arrays.asList(description.toLowerCase(Locale.ROOT).replaceAll("\\p{Punct}", "").split(" "));
        var distinctWords = new HashSet<>(allWords);
        distinctWords.forEach(word -> stats.put(word, Collections.frequency(allWords, word)));

        return stats.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, NoteStatsDto::new));
    }
}