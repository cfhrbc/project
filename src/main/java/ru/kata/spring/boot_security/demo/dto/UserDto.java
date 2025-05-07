package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.dto.RoleDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Schema(description = "DTO пользователя")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Ivan")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "имя должно содержать от 2 до 50 символов")
    private String name;

    @Schema(description = "Пароль пользователя", example = "admin")
    @NotBlank(message = "пароль обязателен")
    @Size(min = 6, max = 25, message = "Пароль должен содержать от 6 до 25 символов")
    private String password;

    @Schema(description = "Фамилия пользователя", example = "Ivanov")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "фамилия должна содержать от 2 до 50 символов")
    private String surname;

    @Schema(description = "Email пользователя", example = "Ivanov23434@bk.ru")
    @Email(message = "Некоректный Email")
    @NotBlank(message = "Email обязателен")
    private String email;

    @Schema(description = "Возраст пользователя", example = "25")
    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    private int age;

    @Schema(description = "Роли пользователя")
    private Set<RoleDto> roles;
}
