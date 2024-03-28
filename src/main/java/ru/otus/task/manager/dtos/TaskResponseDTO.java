package ru.otus.task.manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    @NonNull
    @NotBlank(message = "Task title cannot be blank")
    private String title;
    private String description;
    @NonNull
    @NotNull(message = "Due date must be provided")
    private LocalDate dueDate;
    private Boolean completed;
    private List<NoteResponseDTO> notes;
}