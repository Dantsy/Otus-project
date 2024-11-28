package ru.otus.task.manager.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.task.manager.entities.NoteEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotesRepositoryTests {

    @Mock
    private NotesRepository notesRepository;

    @Test
    public void testFindAllByTaskId() {
        Long taskId = 1L;
        NoteEntity note = NoteEntity.builder().body(String.valueOf(1L)).body("Test Note").build();
        when(notesRepository.findAllByTaskId(taskId)).thenReturn(Optional.of(List.of(note)));

        Optional<List<NoteEntity>> result = notesRepository.findAllByTaskId(taskId);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(note);
        verify(notesRepository).findAllByTaskId(taskId);
    }

    @Test
    public void testFindByTaskIdAndId() {
        Long taskId = 1L;
        Long noteId = 1L;
        NoteEntity note = NoteEntity.builder().body(String.valueOf(noteId)).body("Test Note").build();
        when(notesRepository.findByTaskIdAndId(taskId, noteId)).thenReturn(Optional.of(note));

        Optional<NoteEntity> result = notesRepository.findByTaskIdAndId(taskId, noteId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(note);
        verify(notesRepository).findByTaskIdAndId(taskId, noteId);
    }

    @Test
    public void testDeleteByTaskIdAndId() {
        Long taskId = 1L;
        Long noteId = 1L;

        notesRepository.deleteByTaskIdAndId(taskId, noteId);

        verify(notesRepository).deleteByTaskIdAndId(taskId, noteId);
    }

    @Test
    public void testDeleteAllByTaskId() {
        Long taskId = 1L;

        notesRepository.deleteAllByTaskId(taskId);

        verify(notesRepository).deleteAllByTaskId(taskId);
    }
}
