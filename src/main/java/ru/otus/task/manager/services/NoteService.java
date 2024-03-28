package ru.otus.task.manager.services;

import lombok.NonNull;
import ru.otus.task.manager.entities.NoteEntity;
import ru.otus.task.manager.entities.TaskEntity;
import ru.otus.task.manager.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NotesRepository notesRepository;
    private final TaskService taskService;

    public NoteService(@Autowired @NonNull NotesRepository notesRepository, @Autowired @NonNull TaskService taskService) {
        this.notesRepository = notesRepository;
        this.taskService = taskService;
    }

    public NoteEntity saveNote(NoteEntity newNote) {
        return notesRepository.save(newNote);
    }

    public NoteEntity getNoteById(Long noteId) {
        return notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    public List<NoteEntity> getNotesByTaskId(Long taskId) {
        return notesRepository.findAllByTaskId(taskId)
                .orElseThrow(() -> new NotesNotFoundException(taskId));
    }

    public NoteEntity getNoteByTaskIdAndNoteId(Long taskId, Long noteId) {
        return notesRepository.findByTaskIdAndId(taskId, noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    public NoteEntity createNote(Long taskId, String body) {
        TaskEntity task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new TaskService.TaskNotFoundException(taskId));
        NoteEntity newNote = NoteEntity.builder()
                .body(body)
                .task(task)
                .build();
        return notesRepository.save(newNote);
    }

    @Transactional
    public boolean deleteNoteByTaskIdAndNoteId(Long taskId, Long noteId) {
        Optional<NoteEntity> noteOptional = notesRepository.findByTaskIdAndId(taskId, noteId);
        if (noteOptional.isPresent()) {
            notesRepository.deleteByTaskIdAndId(taskId, noteId);
            return true;
        }
        return false;
    }

    public static class NoteNotFoundException extends RuntimeException {
        public NoteNotFoundException(Long id) {
            super("Note with id " + id + " not found");
        }
    }

    public static class NotesNotFoundException extends RuntimeException {
        public NotesNotFoundException(Long taskId) {
            super("Notes for task with id " + taskId + " not found");
        }
    }
}