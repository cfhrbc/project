package ru.kata.spring.boot_security.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.model.AuthRequest;
import ru.kata.spring.boot_security.demo.model.AuthResponse;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@Tag(name = "CRUD", description = "Методы для работы с пользователеми")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Operation(summary = "Аунтефикация пользователя", description = "Принимает учётные данные и возвращает JWT токен")
    @ApiResponse(responseCode = "200", description = "Успешная аунтефикация, возвращает JWT токен")
    @ApiResponse(responseCode = "401", description = "Ошибка аунтентификации: неверное имя пользователя или пароль")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Parameter(description = "Учетные данные пользователя") AuthRequest authRequest) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            var token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Неверное имя пользователя или пароль");
        }
    }

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping("/users")
    public List<UserDto> showAllUsers() {
        return userService.showAllUsers();
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно получен")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/users/{id}")
    public UserDto getUserId(@PathVariable @Parameter(description = "ID пользователя", example = "1") int id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Создать нового пользователя", description = "Добавляет нового пользователя в систему")
    @ApiResponse(responseCode = "201", description = "Новый пользователь успешно добавлен в систему")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    @PostMapping("/users")
    public UserDto addNewUser(@Valid @RequestBody @Parameter(description = "Данные нового пользователя") UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PutMapping("/users")
    public UserDto updateUser(@Valid @RequestBody @Parameter(description = "ID пользователя", example = "1") UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по ID")
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Parameter(description = "ID пользователя", example = "1") int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Фильтрация и сортировка пользователей", description = "Фильтрует/сортирует пользователей по их полям")
    @ApiResponse(responseCode = "200", description = "Фильтрация/сортировка успошно выполнено")
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    ) {
        List<UserDto> users = userService.getUsersWithFilters(filters, sortBy, sortOrder);
        return ResponseEntity.ok(users);
    }
}


