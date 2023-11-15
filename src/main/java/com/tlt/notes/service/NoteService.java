package com.tlt.notes.service;

import com.tlt.notes.model.Note;
import com.tlt.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Note> findAll() {
        return noteRepository.findAllByOrderByCreatedAtDesc();
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note update(String id, Note note) {
        return noteRepository.save(note);
    }
}