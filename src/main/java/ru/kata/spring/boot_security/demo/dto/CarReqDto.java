package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "DTO Автомобилей")
@AllArgsConstructor
@NoArgsConstructor
public class CarReqDto {

    @Schema(description = "Бренд машины", example = "BMW")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 1, max = 50, message = "Бренд должно содержать от 1 до 50 символов")
    private String brand;

    @Schema(description = "Модель машины", example = "X5")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 1, max = 50, message = "Модель должно содержать от 1 до 50 символов")
    private String model;

    @Schema(description = "Цвет машины", example = "Белая жемчужина")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 1, max = 50, message = "Цвет должно содержать от 1 до 50 символов")
    private String color;

    @Schema(description = "Год выпуска автомобиля", example = "2025")
    @Min(value = 1886, message = "Год выпуска не может быть раньше 1886 года")
    private int year;

    @Schema(description = "ID владельца машины", example = "1")
    private Long ownerId;
}
