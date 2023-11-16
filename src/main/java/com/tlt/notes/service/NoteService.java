package com.tlt.notes.service;

import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<NoteLight> findAll(Pageable pageable) {
        return noteRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note update(String id, Note note) {
        return noteRepository.save(note);
    }
}