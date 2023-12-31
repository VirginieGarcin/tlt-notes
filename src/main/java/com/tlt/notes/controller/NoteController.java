package com.tlt.notes.controller;

import com.tlt.notes.dto.NoteDto;
import com.tlt.notes.dto.NoteLightDto;
import com.tlt.notes.dto.NoteStatsDto;
import com.tlt.notes.model.Note;
import com.tlt.notes.model.NoteLight;
import com.tlt.notes.service.NoteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;
    private final ModelMapper modelMapper;

    public NoteController(NoteService noteService, ModelMapper modelMapper) {
        this.noteService = noteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<NoteLightDto> getNotesList(@PageableDefault Pageable pageable, @RequestParam(required = false) String tag) {
        return noteService.findAll(pageable, tag).map(this::convertToDtoLight);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public NoteDto getNoteById(@PathVariable("id") String id) {
        return convertToDto(noteService.findById(id));
    }

    @GetMapping("/{id}/stats")
    @ResponseBody
    public NoteStatsDto getNoteStats(@PathVariable("id") String id) {
        return noteService.getStats(id);
    }

    @PostMapping("/")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto createNote(@Valid @RequestBody NoteDto noteDto) {
        return convertToDto(noteService.create(convertToEntity(noteDto)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDto updateNote(@PathVariable("id") String id, @Valid @RequestBody NoteDto noteDto) {
        return convertToDto(noteService.update(id, convertToEntity(noteDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable("id") String id) {
        noteService.deleteById(id);
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote() {
        noteService.deleteAll();
    }

    private NoteDto convertToDto(Note note) {
        return modelMapper.map(note, NoteDto.class);
    }

    private NoteLightDto convertToDtoLight(NoteLight note) {
        return modelMapper.map(note, NoteLightDto.class);
    }

    private Note convertToEntity(NoteDto postDto) {
        return modelMapper.map(postDto, Note.class);
    }
}