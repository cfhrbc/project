package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.SocialMediaRequestDto;
import ru.kata.spring.boot_security.demo.dto.SocialMediaResponseDto;
import ru.kata.spring.boot_security.demo.service.SocialMediaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/social-media")
@RequiredArgsConstructor
@Tag(name = "Соцсети", description = "Управление соцсетями пользователя")
@Validated
public class SocialMediaController {

    private final SocialMediaService socialMediaService;


    @Operation(summary = "Создать профиль соцсети", description = "Создаёт новую запись соцсети для указанного пользователя")
    @ApiResponse(responseCode = "201", description = "Профиль соцсети успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    @PostMapping("/social/{userId}")
    public ResponseEntity<SocialMediaResponseDto> create(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long userId,
                                                         @Valid @RequestBody SocialMediaRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(socialMediaService.create(userId, dto));
    }

    @Operation(summary = "Обновить профиль соцсети", description = "Обновляет существующий профиль соцсети по его ID")
    @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Профиль соцсети не найден")
    @PutMapping("/{id}")
    public ResponseEntity<SocialMediaResponseDto> update(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long id,
                                                         @Valid @RequestBody SocialMediaRequestDto dto) {
        return ResponseEntity.ok(socialMediaService.update(id, dto));
    }

    @Operation(summary = "Удалить профиль соцсети",
            description = "Удаляет профиль соцсети по его ID")
    @ApiResponse(responseCode = "204", description = "Профиль успешно удалён")
    @ApiResponse(responseCode = "404", description = "Профиль соцсети не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long id) {
        socialMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить профиль соцсети по ID", description = "Возвращает профиль соцсети по ID")
    @ApiResponse(responseCode = "200", description = "Профиль найден")
    @ApiResponse(responseCode = "404", description = "Профиль не найден")
    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaResponseDto> getById(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long id) {
        return ResponseEntity.ok(socialMediaService.getById(id));
    }

    @Operation(summary = "Получить все профили соцсетей пользователя", description = "Возвращает список всех записей соцсетей по ID пользователя")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SocialMediaResponseDto>> getAllByUserId(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long userId) {
        return ResponseEntity.ok(socialMediaService.getAllByUserId(userId));
    }
}
