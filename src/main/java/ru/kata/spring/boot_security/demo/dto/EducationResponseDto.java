package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponseDto {

    @Schema(description = "Идентификатор соцсети", example = "10")
    private Long id;

    @Schema(description = "Название учреждения")
    private String institution;

    @Schema(description = "Специальность")
    private String degree;

    @Schema(description = "Начало учебного года")
    private Integer startYear;

    @Schema(description = "Конец учебного года")
    private Integer endYear;

    @Schema(description = "Идентификатор пользователя", example = "10")
    private Long userId;
}
