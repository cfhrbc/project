package ru.kata.spring.boot_security.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.model.AuthRequest;
import ru.kata.spring.boot_security.demo.model.AuthResponse;


@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH", description = "Аутентификация пользователя")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

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
}


