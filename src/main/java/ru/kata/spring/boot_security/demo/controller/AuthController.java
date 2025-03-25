package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.AuthRequest;
import ru.kata.spring.boot_security.demo.model.AuthResponse;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
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

    @GetMapping("/users")
    public List<UserDto> showAllUsers() {
        return userService.showAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/users/{id}")
    public UserDto getUserId(@PathVariable int id) {
        var user = userService.findUserById(id);

        return userMapper.toDto(user);
    }

    @PostMapping("/users")
    public UserDto addNewUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);

        return userDto;

    }

    @PutMapping("/users")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return userDto;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok("Пользователь с ID" + id + "был удалён");
    }

}


