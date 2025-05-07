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
import ru.kata.spring.boot_security.demo.dto.FamilyDto;
import ru.kata.spring.boot_security.demo.service.FamilyService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/families")
@RequiredArgsConstructor
@Tag(name = "Семья", description = "Управление членами семьи пользователя")
@Validated
public class FamilyController {

    private final FamilyService familyService;

    @Operation(summary = "Добавить члена семьи")
    @ApiResponse(responseCode = "201", description = "Член семьи добавлен")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PostMapping("/{userId}")
    public ResponseEntity<FamilyDto> createFamily(@PathVariable Long userId, @Valid @RequestBody FamilyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(familyService.create(userId, dto));
    }

    @Operation(summary = "Обновить члена семьи")
    @ApiResponse(responseCode = "200", description = "Данные обновлены")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Член семьи не найден")
    @PutMapping("/{id}")
    public ResponseEntity<FamilyDto> updateFamily(@PathVariable @Min(1) Long id, @Valid @RequestBody FamilyDto dto) {
        return ResponseEntity.ok(familyService.update(id, dto));
    }

    @Operation(summary = "Удалить члена семьи")
    @ApiResponse(responseCode = "204", description = "Удалено")
    @ApiResponse(responseCode = "404", description = "Член семьи не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@Parameter(description = "ID члена семьи", example = "10") @PathVariable Long id) {
        familyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить всех членов семьи пользователя", description = "Возвращает список всех членов семьи по ID пользователя")
    @ApiResponse(responseCode = "200", description = "Список найден")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<FamilyDto>> getAllUserFamilies(@Parameter(description = "ID пользователя", example = "1") @PathVariable("id") Long userId) {
        return ResponseEntity.ok(familyService.getAllByUserId(userId));
    }

    @Operation(summary = "Получить члена семьи по ID")
    @ApiResponse(responseCode = "200", description = "Найдено")
    @ApiResponse(responseCode = "404", description = "Член семьи не найден")
    @GetMapping("/{id}")
    public ResponseEntity<FamilyDto> getByFamilyId(@Parameter(description = "ID члена семьи", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(familyService.getById(id));
    }
}
