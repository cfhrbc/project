package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResDto {

    @Schema(description = "Идентификатор машины", example = "1")
    private Long id;

    @Schema(description = "Бренд машины", example = "BMW")
    private String brand;

    @Schema(description = "Модель машины", example = "X5")
    private String model;

    @Schema(description = "Цвет машины", example = "Красный")
    private String color;

    @Schema(description = "Год выпуска", example = "1998")
    private Integer year;

    @Schema(description = "Имя владельца машины", example = "Ivan Petrov")
    private String ownerName;
}
