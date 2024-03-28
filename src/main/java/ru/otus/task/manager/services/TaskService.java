package ru.otus.task.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.task.manager.entities.TaskEntity;
import ru.otus.task.manager.repositories.TasksRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<TaskEntity> getTasksByCriteria(String title, Boolean completed) {
        if (title != null && completed != null) {
            return tasksRepository.findByTitleAndCompleted(title, completed).orElse(List.of());
        } else if (title != null) {
            return tasksRepository.findByTitle(title).orElse(List.of());
        } else if (completed != null) {
            return tasksRepository.findAllByCompleted(completed).orElse(List.of());
        } else {
            return tasksRepository.findAll();
        }
    }

    public List<TaskEntity> getTasksByCompletionStatus(Boolean completed) {
        return tasksRepository.findAllByCompleted(completed).orElse(List.of());
    }

    public List<TaskEntity> getTasks() {
        return tasksRepository.findAll();
    }

    public Optional<TaskEntity> getTaskById(Long id) {
        return tasksRepository.findById(id);
    }

    public TaskEntity createTask(String title, String description, String dueDate) {
        LocalDate dueDateLocalDate;
        try {
            dueDateLocalDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IncorrectDateException("Due date " + dueDate + " is not in the correct format ('yyyy-MM-dd')");
        }

        if (dueDateLocalDate.isBefore(LocalDate.now())) {
            throw new IncorrectDueDateException("Due date " + dueDate + " cannot be before today");
        }

        TaskEntity task = new TaskEntity(title, description, dueDateLocalDate);
        return tasksRepository.save(task);
    }

    public TaskEntity saveTask(TaskEntity task) {
        return tasksRepository.save(task);
    }

    public TaskEntity updateTask(Long id, String title, String description, String dueDate, Boolean completed) {
        TaskEntity task = getTaskById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (description != null) task.setDescription(description);
        if (title != null) task.setTitle(title);
        if (dueDate != null) {
            try {
                LocalDate date = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
                if (date.isBefore(LocalDate.now())) {
                    throw new IncorrectDueDateException(dueDate);
                }
                task.setDueDate(date);
            } catch (DateTimeParseException e) {
                throw new IncorrectDateException("Due date " + dueDate + " is not in the correct format ('yyyy-MM-dd')");
            }
        }
        if (completed != null) task.setCompleted(completed);
        return tasksRepository.save(task);
    }

    public TaskEntity deleteTask(Long id) {
        TaskEntity task = getTaskById(id).orElseThrow(() -> new TaskNotFoundException(id));
        tasksRepository.deleteById(id);
        return task;
    }

    public static class TaskNotFoundException extends RuntimeException {
        public TaskNotFoundException(long id) {
            super("Task with id " + id + " not found");
        }
    }

    public static class IncorrectDateException extends RuntimeException {
        public IncorrectDateException(String date) {
            super("Format of " + date + " is not correct. It should be ('yyyy-MM-dd')");
        }
    }

    public static class IncorrectDueDateException extends RuntimeException {
        public IncorrectDueDateException(String date) {
            super("Due date " + date + " is not valid");
        }
    }
}