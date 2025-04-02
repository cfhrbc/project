package ru.kata.spring.boot_security.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Schema(description = "DTO ролей")
@NoArgsConstructor
public class RoleDto {

    @Schema(description = "Идентификатор роли", example = "1")
    private int id;

    @Schema(description = "Имя роли", example = "ROLE_ADMIN")
    private String name;
}
