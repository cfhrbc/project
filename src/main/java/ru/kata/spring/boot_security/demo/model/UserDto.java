package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "имя должно содержать от 2 до 50 символов")
    private String name;

    @NotBlank(message = "пароль обязателен")
    @Size(min = 6, max = 25, message = "Пароль должен содержать от 6 до 25 символов")
    private String password;

    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "фамилия должна содержать от 2 до 50 символов")
    private String surname;

    @Email(message = "Некоректный Email")
    @NotBlank(message = "Email обязателен")
    private String email;

    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    private int age;

    private Set<RoleDto> roles;
}
