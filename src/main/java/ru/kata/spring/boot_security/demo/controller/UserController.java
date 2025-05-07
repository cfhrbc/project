package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "CRUD", description = "Методы для работы с пользователеми")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping()
    public List<UserDto> showAllUsers() {
        return userService.showAllUsers();
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно получен")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/{id}")
    public UserDto getUserId(@PathVariable @Parameter(description = "ID пользователя", example = "1") Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Создать нового пользователя", description = "Добавляет нового пользователя в систему")
    @ApiResponse(responseCode = "201", description = "Новый пользователь успешно добавлен в систему")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    @PostMapping()
    public UserDto addNewUser(@Valid @RequestBody @Parameter(description = "Данные нового пользователя") UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PutMapping()
    public UserDto updateUser(@Valid @RequestBody @Parameter(description = "ID пользователя", example = "1") UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по ID")
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Parameter(description = "ID пользователя", example = "1") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Фильтрация и сортировка пользователей", description = "Фильтрует/сортирует пользователей по их полям")
    @ApiResponse(responseCode = "200", description = "Фильтрация/сортировка успошно выполнено")
    @GetMapping("/all")
    public List<UserDto> getUsers(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    ) {
        return userService.getUsersWithFilters(filters, sortBy, sortOrder);
    }
}
