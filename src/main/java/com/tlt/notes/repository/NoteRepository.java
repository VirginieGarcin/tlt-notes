package com.tlt.notes.repository;

import com.tlt.notes.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
    Page<Note> findAllByOrderByCreatedAtDesc(Pageable pageRequest);
}