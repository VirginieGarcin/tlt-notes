package com.tlt.notes.service;

import com.tlt.notes.dto.NoteStatsDto;
import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.repository.NoteRepository;
import jakarta.validation.constraints.NotBlank;
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
        noteRepository.deleteById(id);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }

    public Optional<Note> findById(String id) {
        return noteRepository.findById(id);
    }

    public Page<NoteLight> findAll(Pageable pageable, String tagFilter) {
        if (tagFilter == null) {
            return noteRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            return noteRepository.findAllByTagsContainsOrderByCreatedAtDesc(pageable, tagFilter);
        }
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note update(String id, Note note) {
        return noteRepository.save(note);
    }

    public NoteStatsDto getStats(String id) {
        var note = findById(id).orElse(null);
        if (note != null && StringUtils.hasText(note.getDescription())) {
            return calculateStats(note.getDescription());
        } else {
            return null;
        }
    }

    NoteStatsDto calculateStats(@NotBlank String description) {
        var stats = new NoteStatsDto();
        var allWords = Arrays.asList(description.split(" "));
        var distinctWords = new HashSet<>(allWords);
        distinctWords.forEach(word -> stats.put(word, Collections.frequency(allWords, word)));

        return stats.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, NoteStatsDto::new));
    }
}