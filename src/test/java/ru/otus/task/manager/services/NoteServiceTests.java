package ru.otus.task.manager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.task.manager.entities.NoteEntity;
import ru.otus.task.manager.entities.TaskEntity;
import ru.otus.task.manager.repositories.NotesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void testSaveNote() {
        NoteEntity note = new NoteEntity();
        note.setBody("Test note");
        when(notesRepository.save(any(NoteEntity.class))).thenReturn(note);

        NoteEntity savedNote = noteService.saveNote(note);

        assertThat(savedNote).isNotNull();
        assertThat(savedNote).isEqualTo(note);
        verify(notesRepository).save(any(NoteEntity.class));
    }

    @Test
    public void testGetNoteById() {
        Long noteId = 1L;
        NoteEntity note = new NoteEntity();
        note.setId(noteId);
        note.setBody("Test note");
        when(notesRepository.findById(noteId)).thenReturn(Optional.of(note));

        NoteEntity result = noteService.getNoteById(noteId);

        assertThat(result).isEqualTo(note);
        verify(notesRepository).findById(noteId);
    }

    @Test
    public void testGetNoteByIdNotFound() {
        Long noteId = 1L;
        when(notesRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.getNoteById(noteId))
                .isInstanceOf(NoteService.NoteNotFoundException.class)
                .hasMessage("Note with id " + noteId + " not found");
        verify(notesRepository).findById(noteId);
    }

    @Test
    public void testGetNotesByTaskId() {
        Long taskId = 1L;
        NoteEntity note1 = new NoteEntity();
        note1.setId(1L);
        note1.setBody("Note 1");
        NoteEntity note2 = new NoteEntity();
        note2.setId(2L);
        note2.setBody("Note 2");
        List<NoteEntity> notes = Arrays.asList(note1, note2);
        when(notesRepository.findAllByTaskId(taskId)).thenReturn(Optional.of(notes));

        List<NoteEntity> result = noteService.getNotesByTaskId(taskId);

        assertThat(result).isEqualTo(notes);
        verify(notesRepository).findAllByTaskId(taskId);
    }

    @Test
    public void testGetNotesByTaskIdNotFound() {
        Long taskId = 1L;
        when(notesRepository.findAllByTaskId(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.getNotesByTaskId(taskId))
                .isInstanceOf(NoteService.NotesNotFoundException.class)
                .hasMessage("Notes for task with id " + taskId + " not found");
        verify(notesRepository).findAllByTaskId(taskId);
    }

    @Test
    public void testGetNoteByTaskIdAndNoteId() {
        Long taskId = 1L;
        Long noteId = 1L;
        NoteEntity note = new NoteEntity();
        note.setId(noteId);
        note.setBody("Test note");
        when(notesRepository.findByTaskIdAndId(taskId, noteId)).thenReturn(Optional.of(note));

        NoteEntity result = noteService.getNoteByTaskIdAndNoteId(taskId, noteId);

        assertThat(result).isEqualTo(note);
        verify(notesRepository).findByTaskIdAndId(taskId, noteId);
    }

    @Test
    public void testGetNoteByTaskIdAndNoteIdNotFound() {
        Long taskId = 1L;
        Long noteId = 1L;
        when(notesRepository.findByTaskIdAndId(taskId, noteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.getNoteByTaskIdAndNoteId(taskId, noteId))
                .isInstanceOf(NoteService.NoteNotFoundException.class)
                .hasMessage("Note with id " + noteId + " not found");
        verify(notesRepository).findByTaskIdAndId(taskId, noteId);
    }

    @Test
    public void testCreateNote() {
        Long taskId = 1L;
        String body = "New note";
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        NoteEntity note = new NoteEntity();
        note.setBody(body);
        note.setTask(task);
        when(notesRepository.save(any(NoteEntity.class))).thenReturn(note);

        NoteEntity createdNote = noteService.createNote(taskId, body);

        assertThat(createdNote).isEqualTo(note);
        verify(taskService).getTaskById(taskId);
        verify(notesRepository).save(any(NoteEntity.class));
    }

    @Test
    public void testCreateNoteTaskNotFound() {
        Long taskId = 1L;
        String body = "New note";
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.createNote(taskId, body))
                .isInstanceOf(TaskService.TaskNotFoundException.class)
                .hasMessage("Task with id " + taskId + " not found");
        verify(taskService).getTaskById(taskId);
        verifyNoMoreInteractions(notesRepository);
    }

    @Test
    public void testDeleteNoteByTaskIdAndNoteId() {
        Long taskId = 1L;
        Long noteId = 1L;
        NoteEntity note = new NoteEntity();
        note.setId(noteId);
        note.setBody("Test note");
        when(notesRepository.findByTaskIdAndId(taskId, noteId)).thenReturn(Optional.of(note));

        boolean result = noteService.deleteNoteByTaskIdAndNoteId(taskId, noteId);

        assertThat(result).isTrue();
        verify(notesRepository).findByTaskIdAndId(taskId, noteId);
        verify(notesRepository).deleteByTaskIdAndId(taskId, noteId);
    }

    @Test
    public void testDeleteNoteByTaskIdAndNoteIdNotFound() {
        Long taskId = 1L;
        Long noteId = 1L;
        when(notesRepository.findByTaskIdAndId(taskId, noteId)).thenReturn(Optional.empty());

        boolean result = noteService.deleteNoteByTaskIdAndNoteId(taskId, noteId);

        assertThat(result).isFalse();
        verify(notesRepository).findByTaskIdAndId(taskId, noteId);
        verifyNoMoreInteractions(notesRepository);
    }

}