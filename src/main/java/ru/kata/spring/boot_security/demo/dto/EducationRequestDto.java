package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO Для образования")
public class EducationRequestDto {

    @NotBlank(message = "Название учреждения не может быть пустым")
    private String institution;

    @NotBlank(message = "Специальность не может быть пустой")
    private String degree;

    @Min(value = 1900)
    private Integer startYear;

    @Min(value = 1900)
    private Integer endYear;


}

