package ru.otus.task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.task.manager.dtos.CreateTaskDTO;
import ru.otus.task.manager.dtos.TaskResponseDTO;
import ru.otus.task.manager.dtos.UpdateTaskDTO;
import ru.otus.task.manager.entities.TaskEntity;
import ru.otus.task.manager.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "completed", required = false) Boolean completed) {
        List<TaskEntity> tasks = taskService.getTasksByCriteria(title, completed);
        List<TaskResponseDTO> responseDTOs = tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskDTO taskDTO) {
        TaskEntity newTask = taskService.createTask(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate().toString());
        TaskEntity savedNewTask = taskService.saveTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedNewTask));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        Optional<TaskEntity> taskOptional = taskService.getTaskById(id);
        return taskOptional.map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> modifyTask(@PathVariable("id") Long id, @RequestBody UpdateTaskDTO taskDTO) {
        TaskEntity updatedTask = taskService.updateTask(id, taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate().toString(), taskDTO.getCompleted());
        return ResponseEntity.ok(convertToDTO(updatedTask));
    }

    private TaskResponseDTO convertToDTO(TaskEntity task) {
        TaskResponseDTO taskDTO = new TaskResponseDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCompleted(task.getCompleted());
        return taskDTO;
    }
}