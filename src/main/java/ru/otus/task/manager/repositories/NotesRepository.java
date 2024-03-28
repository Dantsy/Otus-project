package ru.otus.task.manager.repositories;

import ru.otus.task.manager.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<NoteEntity, Long> {

    Optional<List<NoteEntity>> findAllByTaskId(Long taskId);

    Optional<NoteEntity> findByTaskIdAndId(Long taskId, Long noteId);

    void deleteByTaskIdAndId(Long taskId, Long noteId);

    void deleteAllByTaskId(Long taskId);
}