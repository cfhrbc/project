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
import ru.kata.spring.boot_security.demo.dto.HouseDto;
import ru.kata.spring.boot_security.demo.service.HouseService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
@Tag(name = "Дом", description = "Управление домами пользователя")
@Validated
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "Добавить дом")
    @ApiResponse(responseCode = "201", description = "Дом добавлен")
    @PostMapping("/user/{userId}")
    public ResponseEntity<HouseDto> createHouse(@Parameter(description = "ID хозяина", example = "10") @PathVariable Long userId,@RequestBody @Valid HouseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.create(userId, dto));
    }

    @Operation(summary = "Обновить дом")
    @ApiResponse(responseCode = "200", description = "Дом обновлён")
    @ApiResponse(responseCode = "404", description = "Дом не найден")
    @PutMapping("/{id}")
    public ResponseEntity<HouseDto> updateHouse(@Parameter(description = "ID дома", example = "10") @PathVariable @Min(1) Long id, @Valid @RequestBody HouseDto dto) {
        return ResponseEntity.ok(houseService.update(id, dto));
    }

    @Operation(summary = "Удалить дом")
    @ApiResponse(responseCode = "204", description = "Дом удалён")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@Parameter(description = "ID дома", example = "10") @PathVariable Long id) {
        houseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить дом по ID")
    @ApiResponse(responseCode = "200", description = "Дом найден")
    @ApiResponse(responseCode = "404", description = "Дом не найден")
    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> getByHouseId(@Parameter(description = "ID дома", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(houseService.getById(id));
    }

    @Operation(summary = "Получить все дома пользователя")
    @ApiResponse(responseCode = "200", description = "Список домов найден")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<HouseDto>> getAllUserHouses(@Parameter(description = "ID хозяина дома", example = "10") @PathVariable("id") Long userId) {
        return ResponseEntity.ok(houseService.getAllByUserId(userId));
    }
}
