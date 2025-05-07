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
import ru.kata.spring.boot_security.demo.dto.CarReqDto;
import ru.kata.spring.boot_security.demo.dto.CarResDto;
import ru.kata.spring.boot_security.demo.service.CarService;

import javax.validation.Valid;


@RestController
@RequestMapping("/cars")
@Tag(name = "Car Controller", description = "Управление автомобилями")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    @Operation(summary = "Получить машину по id")
    @ApiResponse(responseCode = "200", description = "Автомобиль успешно получен")
    @ApiResponse(responseCode = "404", description = "Автомобиль не найден")
    @GetMapping("/{id}")
    public ResponseEntity<CarResDto> getCarById(@PathVariable @Parameter(description = "ID пользователя", example = "1") Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @Operation(summary = "Создать новую машину")
    @ApiResponse(responseCode = "201", description = "Новый Автомобиль успешно добавлен в систему")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    @PostMapping
    public ResponseEntity<CarResDto> createCar(@Valid @RequestBody @Parameter(description = "Данные нового пользователя") CarReqDto carReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(carReqDto));
    }

    @Operation(summary = "Обновить машину по id")
    @ApiResponse(responseCode = "200", description = "Автомобиль успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Автомобиль не найден")
    @PutMapping("/{id}")
    public ResponseEntity<CarResDto> updateCar(@PathVariable @Parameter(description = "ID пользователя", example = "1") Long id, @Valid @RequestBody CarReqDto carReqDto) {
        return ResponseEntity.ok(carService.updateCar(id, carReqDto));
    }

    @Operation(summary = "Удалить машину по id")
    @ApiResponse(responseCode = "204", description = "Автомобиль успешно удалён")
    @ApiResponse(responseCode = "404", description = "Автомобиль не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable @Parameter(description = "ID пользователя", example = "1") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
