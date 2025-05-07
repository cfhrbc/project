package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;
import ru.kata.spring.boot_security.demo.service.EducationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/education")
@Validated
@Tag(name = "Образование", description = "CRUD операции для образования пользователя")
public class EducationController {

    private final EducationService service;

    @Operation(summary = "Создать запись об образовании", description = "Создаёт новую запись об образовании для указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Запись успешно создана")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    @PostMapping("/{userId}")
    public ResponseEntity<EducationResponseDto> create(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long userId,
                                                       @RequestBody @Valid EducationRequestDto dto) {
        return ResponseEntity.ok(service.create(userId, dto));
    }

    @Operation(summary = "Обновить запись об образовании", description = "Обновляет существующую запись об образовании по её ID")
    @ApiResponse(responseCode = "200", description = "Запись успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @PutMapping("/{id}")
    public ResponseEntity<EducationResponseDto> update(@Parameter(name = "id", description = "ID записи об образовании")@PathVariable Long id,
                                                       @RequestBody @Valid EducationRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Удалить запись об образовании", description = "Удаляет запись об образовании по её ID")
    @ApiResponse(responseCode = "204", description = "Запись успешно удалена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(name = "id", description = "ID записи об образовании")@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить все записи об образовании пользователя", description = "Возвращает список всех записей об образовании для заданного пользователя")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @Parameter(name = "userId", description = "ID пользователя", required = true)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EducationResponseDto>> getAllByUser(@Parameter(name = "userId", description = "ID пользователя")@PathVariable Long userId) {
        return ResponseEntity.ok(service.getAllByUserId(userId));
    }

    @Operation(summary = "Получить запись об образовании по ID",
            description = "Возвращает запись об образовании по её ID")
    @ApiResponse(responseCode = "200", description = "Запись найдена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<EducationResponseDto> getById(@Parameter(name = "id", description = "ID записи об образовании")@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
