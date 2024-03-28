package ru.otus.task.manager.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.task.manager.entities.TaskEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TasksRepositoryTests {

    @Mock
    private TasksRepository tasksRepository;

    @Test
    public void testFindAllByTitle() {
        String title = "Test Title";
        TaskEntity task = TaskEntity.builder().title(title).build();
        when(tasksRepository.findAllByTitle(title)).thenReturn(Optional.of(List.of(task)));

        Optional<List<TaskEntity>> result = tasksRepository.findAllByTitle(title);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(task);
        verify(tasksRepository).findAllByTitle(title);
    }

    @Test
    public void testFindAllByCompleted() {
        Boolean completed = true;
        TaskEntity task = TaskEntity.builder().completed(completed).build();
        when(tasksRepository.findAllByCompleted(completed)).thenReturn(Optional.of(List.of(task)));

        Optional<List<TaskEntity>> result = tasksRepository.findAllByCompleted(completed);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(task);
        verify(tasksRepository).findAllByCompleted(completed);
    }

    @Test
    public void testFindAllByCompletedAndDueDateBefore() {
        Boolean completed = true;
        LocalDate dueDate = LocalDate.now();
        TaskEntity task = TaskEntity.builder().completed(completed).dueDate(dueDate).build();
        when(tasksRepository.findAllByCompletedAndDueDateBefore(completed, dueDate)).thenReturn(Optional.of(List.of(task)));

        Optional<List<TaskEntity>> result = tasksRepository.findAllByCompletedAndDueDateBefore(completed, dueDate);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(task);
        verify(tasksRepository).findAllByCompletedAndDueDateBefore(completed, dueDate);
    }

    @Test
    public void testFindByTitle() {
        String title = "Test Title";
        TaskEntity task = TaskEntity.builder().title(title).build();
        when(tasksRepository.findByTitle(title)).thenReturn(Optional.of(List.of(task)));

        Optional<List<TaskEntity>> result = tasksRepository.findByTitle(title);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(task);
        verify(tasksRepository).findByTitle(title);
    }

    @Test
    public void testFindByTitleAndCompleted() {
        String title = "Test Title";
        Boolean completed = true;
        TaskEntity task = TaskEntity.builder().title(title).completed(completed).build();
        when(tasksRepository.findByTitleAndCompleted(title, completed)).thenReturn(Optional.of(List.of(task)));

        Optional<List<TaskEntity>> result = tasksRepository.findByTitleAndCompleted(title, completed);

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly(task);
        verify(tasksRepository).findByTitleAndCompleted(title, completed);
    }
}