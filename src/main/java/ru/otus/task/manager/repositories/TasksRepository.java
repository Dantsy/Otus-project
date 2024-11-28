package ru.otus.task.manager.repositories;

import ru.otus.task.manager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findAllByTitle(String title);

    Optional<List<TaskEntity>> findAllByCompleted(Boolean completed);

    Optional<List<TaskEntity>> findAllByCompletedAndDueDateBefore(Boolean completed, LocalDate dueDate);

    Optional<List<TaskEntity>> findByTitle(String title);

    Optional<List<TaskEntity>> findByTitleAndCompleted(String title, Boolean completed);
}