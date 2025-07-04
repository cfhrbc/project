package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaResponseDto {

    @Schema(description = "Идентификатор соцсети", example = "10")
    private Long id;

    @Schema(description = "Имя социальной сети", example = "Вконтакте")
    private String platform;

    @Schema(description = "Ник-нейм пользователя", example = "admin")
    private String username;

    @Schema(description = "URL пользователя", example = "https://vk.com/example")
    private String url;

    @Schema(description = "ID пользователя", example = "1")
    private Long userId;
}
