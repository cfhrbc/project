package ru.kata.spring.boot_security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO Социальных сетей")
public class SocialMediaRequestDto {

    @Schema(description = "Имя социальной сети", example = "Вконтакте")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    private String platform;

    @Schema(description = "Ник-нейм пользователя", example = "admin")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 6, max = 25, message = "Ник-нейм должен содержать от 6 до 25 символов")
    private String username;

    @Schema(description = "URL пользователя", example = "https://vk.com/example")
    @NotBlank(message = "Строка не может быть пустой")
    @Size(min = 2, max = 50, message = "URL должен содержать от 2 до 50 символов")
    private String url;
}
