package ru.otus.task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.task.manager.dtos.CreateNoteDTO;
import ru.otus.task.manager.dtos.NoteResponseDTO;
import ru.otus.task.manager.entities.NoteEntity;
import ru.otus.task.manager.services.NoteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getNotes(@PathVariable("taskId") Long taskId) {
        List<NoteEntity> notes = noteService.getNotesByTaskId(taskId);
        List<NoteResponseDTO> noteDTOs = notes.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(noteDTOs);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@PathVariable("taskId") Long taskId, @RequestBody CreateNoteDTO noteDTO) {
        NoteEntity newNote = noteService.createNote(taskId, noteDTO.getBody());
        NoteEntity newSavedNote = noteService.saveNote(newNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newSavedNote));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable("taskId") Long taskId, @PathVariable("noteId") Long noteId) {
        NoteEntity note = noteService.getNoteByTaskIdAndNoteId(taskId, noteId);
        return ResponseEntity.ok(convertToDTO(note));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable("taskId") Long taskId, @PathVariable("noteId") Long noteId) {
        boolean deleted = noteService.deleteNoteByTaskIdAndNoteId(taskId, noteId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private NoteResponseDTO convertToDTO(NoteEntity note) {
        NoteResponseDTO noteDTO = new NoteResponseDTO();
        noteDTO.setBody(note.getBody());
        noteDTO.setId(note.getId());
        noteDTO.setTaskId(note.getTask().getId());
        return noteDTO;
    }
}