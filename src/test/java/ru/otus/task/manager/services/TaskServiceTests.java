package ru.otus.task.manager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.task.manager.entities.TaskEntity;
import ru.otus.task.manager.repositories.TasksRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetTasksByCriteria() {
        String title = "Test Title";
        Boolean completed = true;
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findByTitleAndCompleted(title, completed)).thenReturn(Optional.of(List.of(task)));

        List<TaskEntity> result = taskService.getTasksByCriteria(title, completed);

        assertThat(result).containsExactly(task);
        verify(tasksRepository).findByTitleAndCompleted(title, completed);
    }

    @Test
    public void testGetTasksByCompletionStatus() {
        Boolean completed = true;
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findAllByCompleted(completed)).thenReturn(Optional.of(List.of(task)));

        List<TaskEntity> result = taskService.getTasksByCompletionStatus(completed);

        assertThat(result).containsExactly(task);
        verify(tasksRepository).findAllByCompleted(completed);
    }

    @Test
    public void testGetTasks() {
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findAll()).thenReturn(List.of(task));

        List<TaskEntity> result = taskService.getTasks();

        assertThat(result).containsExactly(task);
        verify(tasksRepository).findAll();
    }

    @Test
    public void testGetTaskById() {
        Long id = 1L;
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findById(id)).thenReturn(Optional.of(task));

        Optional<TaskEntity> result = taskService.getTaskById(id);

        assertThat(result).contains(task);
        verify(tasksRepository).findById(id);
    }

    @Test
    public void testCreateTask_withIncorrectDateFormat() {
        String title = "Test Title";
        String description = "Test Description";
        String dueDate = "31-12-2022";

        assertThatThrownBy(() -> taskService.createTask(title, description, dueDate))
                .isInstanceOf(TaskService.IncorrectDateException.class)
                .hasMessageContaining("Due date " + dueDate + " is not in the correct format ('yyyy-MM-dd')");
        verify(tasksRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    public void testCreateTask_withPastDueDate() {
        String title = "Test Title";
        String description = "Test Description";
        String dueDate = "2020-12-31";

        assertThatThrownBy(() -> taskService.createTask(title, description, dueDate))
                .isInstanceOf(TaskService.IncorrectDueDateException.class)
                .hasMessageContaining("Due date " + dueDate + " cannot be before today");
        verify(tasksRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    public void testSaveTask() {
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.save(task)).thenReturn(task);

        TaskEntity result = taskService.saveTask(task);

        assertThat(result).isEqualTo(task);
        verify(tasksRepository).save(task);
    }

    @Test
    public void testUpdateTask_withNonExistingTask() {
        Long id = 1L;
        when(tasksRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(id, null, null, null, null))
                .isInstanceOf(TaskService.TaskNotFoundException.class)
                .hasMessageContaining("Task with id " + id + " not found");
        verify(tasksRepository).findById(id);
        verify(tasksRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    public void testUpdateTask_withIncorrectDueDate() {
        Long id = 1L;
        String title = "Updated Title";
        String description = "Updated Description";
        String dueDate = "2020-12-31";
        Boolean completed = true;
        TaskEntity existingTask = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findById(id)).thenReturn(Optional.of(existingTask));

        assertThatThrownBy(() -> taskService.updateTask(id, title, description, dueDate, completed))
                .isInstanceOf(TaskService.IncorrectDueDateException.class)
                .hasMessageContaining("Due date " + dueDate + " is not valid");
        verify(tasksRepository).findById(id);
        verify(tasksRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    public void testDeleteTask() {
        Long id = 1L;
        TaskEntity task = new TaskEntity("Test Title", "Test Description", LocalDate.now());
        when(tasksRepository.findById(id)).thenReturn(Optional.of(task));

        TaskEntity result = taskService.deleteTask(id);

        assertThat(result).isEqualTo(task);
        verify(tasksRepository).findById(id);
        verify(tasksRepository).deleteById(id);
    }

    @Test
    public void testDeleteTask_withNonExistingTask() {
        Long id = 1L;
        when(tasksRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.deleteTask(id))
                .isInstanceOf(TaskService.TaskNotFoundException.class)
                .hasMessageContaining("Task with id " + id + " not found");
        verify(tasksRepository).findById(id);
        verify(tasksRepository, never()).deleteById(anyLong());
    }
}