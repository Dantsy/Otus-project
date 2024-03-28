package ru.otus.task.manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {
    @NonNull
    @NotBlank(message = "Task title cannot be blank")
    private String title;

    private String description;

    @NonNull
    @NotNull(message = "Due date must be provided")
    private LocalDate dueDate;
}