package ru.kata.spring.boot_security.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.model.AuthRequest;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.MediaType;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void authenticateUser_success() throws Exception {
        AuthRequest authRequest = new AuthRequest("admin", "admin");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(jwtTokenProvider.generateToken(authentication)).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void authenticateUser_fail() throws Exception {
        var authRequest = new AuthRequest("admin", "wrong-password");

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException(""));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllUsers_success() throws Exception {
        List<UserDto> users = List.of(new UserDto("admin", "admin@example.com"));

        Mockito.when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("admin");
        Mockito.when(userService.showAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("admin"));
    }

    @Test
    void updateUser_success() throws Exception {
        UserDto user = new UserDto(1, "updatedUser", "updated@example.com");

        Mockito.when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("admin");
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(user);

        mockMvc.perform(put("/users")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));
    }

    @Test
    void createUser_success() throws Exception {
        UserDto user = new UserDto(0, "newuser", "newuser@example.com");
        UserDto saved = new UserDto(1, "newuser", "newuser@example.com");

        Mockito.when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("admin");
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void getUserById_success() throws Exception {
        UserDto user = new UserDto(1, "admin", "admin@example.com");

        Mockito.when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("admin");
        Mockito.when(userService.findUserById(1)).thenReturn(user);

        mockMvc.perform(get("/users/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"));
    }



    @Test
    void deleteUser_success() throws Exception {
        Mockito.when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("admin");
        Mockito.doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/users/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }
}
