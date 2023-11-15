package com.tlt.notes.repository;

import com.tlt.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findAllByOrderByCreatedAtDesc();
}