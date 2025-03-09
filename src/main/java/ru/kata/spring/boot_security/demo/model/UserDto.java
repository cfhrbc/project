package ru.kata.spring.boot_security.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    public UserDto() {
    }

    public UserDto(Integer id, String name, String password, String surname, String email, int age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
