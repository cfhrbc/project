package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponseDto {

    @Schema(description = "Идентификатор образовательного учреждения", example = "10")
    private Long id;

    @Schema(description = "Название учреждения")
    private String institution;

    @Schema(description = "Специальность")
    private String degree;

    @Schema(description = "Начало обучения")
    private Integer startYear;

    @Schema(description = "Конец обучения")
    private Integer endYear;

    @Schema(description = "Список идентификаторов пользователей", example = "[1, 2, 3]")
    private Set<Long> usersId;
}
