package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseDto {

    private Long id;

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @Positive(message = "Площадь должна быть положительной")
    private Double area;

    @Min(value = 1800, message = "Год постройки должен быть не раньше 1800")
    @Max(value = 2025, message = "Год постройки должен быть не позже 2025")
    private Integer constructionYear;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;
}

