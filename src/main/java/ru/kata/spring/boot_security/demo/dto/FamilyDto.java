package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO семьи")
public class FamilyDto {

    @Schema(description = "Идентификатор члена семьи", example = "1")
    private Long id;

    @Schema(description = "Родственная связь (например: Мать, Отец)", example = "Мать")
    @NotBlank(message = "Связь не может быть пустой")
    @Size(min = 2, max = 50, message = "Связь должна быть от 2 до 50 символов")
    private String relation;

    @Schema(description = "Имя члена семьи", example = "Анна")
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String name;

    @Schema(description = "Возраст члена семьи", example = "45")
    @NotNull(message = "Возраст обязателен")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 120, message = "Возраст не может превышать 120")
    private Integer age;

    @Schema(description = "Номер телефона", example = "+79991234567")
    @Pattern(regexp = "\\+7\\d{10}", message = "Номер телефона должен начинаться с +7 и содержать 11 цифр")
    private String phoneNumber;

    @Schema(description = "ID пользователя, к которому относится родственник", example = "10")
    @NotNull(message = "ID пользователя обязателен")
    private Long userId;
}
