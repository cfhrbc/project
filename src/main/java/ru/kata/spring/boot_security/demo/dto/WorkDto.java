package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO работы пользователя")
public class WorkDto {

    @Schema(description = "Идентификатор работы", example = "1")
    private Long id;

    @NotBlank(message = "Компания не может быть пустым")
    @Size(max = 100, message = "Компания не может быть длиннее 100 символов")
    private String company;

    @NotBlank(message = "Должность не может быть пустой")
    @Size(max = 50, message = "Должность не может быть длиннее 50 символов")
    private String position;

    @NotBlank(message = "Дата начала не может быть пустой")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Дата начала должна быть в формате yyyy-MM-dd")
    private String startDate;

    @Schema(description = "Дата окончания работы. Если пользователь всё ещё работает — может быть null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Дата окончания должна быть в формате yyyy-MM-dd")
    private String endDate;
}
