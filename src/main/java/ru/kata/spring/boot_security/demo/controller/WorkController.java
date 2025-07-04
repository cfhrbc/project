package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.service.WorkService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
@Tag(name = "CRUD", description = "Методы по деятельности пользователей")
public class WorkController {

    private final WorkService workService;

    @Operation(summary = "Создать запись о работе", description = "Создаёт новую запись о работе для пользователя по ID")
    @ApiResponse(responseCode = "201", description = "Запись успешно создана")
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    @PostMapping("/{userId}")
    public ResponseEntity<WorkDto> create(@Parameter(name = "userId", description = "ID пользователя") @PathVariable Long userId,
                                          @RequestBody @Valid WorkDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.create(userId, dto));
    }

    @Operation(summary = "Обновить запись о работе", description = "Обновляет существующую запись о работе по ID")
    @ApiResponse(responseCode = "200", description = "Запись успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @PutMapping("/{id}")
    public ResponseEntity<WorkDto> update(@Parameter(name = "id", description = "ID записи о работе") @PathVariable Long id,
                                          @RequestBody @Valid WorkDto dto) {
        return ResponseEntity.ok(workService.update(id, dto));
    }

    @Operation(summary = "Удалить запись о работе", description = "Удаляет запись о работе по ID")
    @ApiResponse(responseCode = "204", description = "Запись успешно удалена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(name = "id", description = "ID записи о работе") @PathVariable Long id) {
        workService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить запись о работе по ID", description = "Возвращает одну запись о работе по ID")
    @ApiResponse(responseCode = "200", description = "Запись найдена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<WorkDto> getById(@Parameter(name = "id", description = "ID записи о работе") @PathVariable Long id) {
        return ResponseEntity.ok(workService.findById(id));
    }

    @Operation(summary = "Получить все записи по пользователю", description = "Возвращает список всех записей о работе для указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping("/user/{userId}")
    public ResponseEntity<WorkDto> getAllByUser(@Parameter(name = "userId", description = "ID пользователя") @PathVariable Long userId) {
        return workService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
