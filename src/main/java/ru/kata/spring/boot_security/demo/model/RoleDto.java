package ru.kata.spring.boot_security.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "DTO ролей")
@NoArgsConstructor
public class RoleDto {

    @Schema(description = "Идентификатор роли", example = "1")
    private Long id;

    @Schema(description = "Имя роли", example = "ROLE_ADMIN")
    private String name;
}
