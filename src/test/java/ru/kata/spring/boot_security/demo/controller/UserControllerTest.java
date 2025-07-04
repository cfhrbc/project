package ru.kata.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testShowAllUsers() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var userDto = UserDto.builder()
                .id(1L)
                .name("User")
                .surname("Surname")
                .password("password")
                .email("user@mail.com")
                .age(25)
                .roles(Set.of())
                .build();

        var users = List.of(userDto);

        when(userService.showAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("User"))
                .andExpect(jsonPath("$[0].email").value("user@mail.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var user = UserDto.builder()
                .id(1L)
                .name("Test")
                .surname("admin")
                .password("pass123")
                .email("test@mail.com")
                .age(22)
                .roles(Set.of())
                .build();

        when(userService.findUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void testAddNewUser() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = UserDto.builder()
                .name("New")
                .surname("User")
                .password("pass123")
                .email("new@mail.com")
                .age(25)
                .roles(Set.of())
                .build();

        var saved = UserDto.builder()
                .id(1L)
                .name("New")
                .surname("User")
                .password("pass123")
                .email("new@mail.com")
                .age(25)
                .roles(Set.of())
                .build();

        when(userService.saveUser(any(UserDto.class))).thenReturn(saved);

        mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var updated = UserDto.builder()
                .id(1L)
                .name("Updated")
                .surname("User")
                .password("password")
                .email("updated@mail.com")
                .age(30)
                .roles(Set.of())
                .build();

        when(userService.saveUser(any(UserDto.class))).thenReturn(updated);

        mockMvc.perform(put("/users")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void deleteUser() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetUsersWithFiltersAndSorting() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var user1 = UserDto.builder()
                .id(1L)
                .name("Alice")
                .surname("Smith")
                .email("alice@mail.com")
                .age(30)
                .roles(Set.of())
                .build();

        var user2 = UserDto.builder()
                .id(2L)
                .name("Bob")
                .surname("Johnson")
                .email("bob@mail.com")
                .age(25)
                .roles(Set.of())
                .build();

        var filteredUsers = List.of(user2, user1);

        var filters = new HashMap<>();
        filters.put("surname", "Smith");

        when(userService.getUsersWithFilters(anyMap(), eq("age"), eq("asc"))).thenReturn(filteredUsers);

        mockMvc.perform(get("/users/all")
                        .param("surname", "Smith")
                        .param("sortBy", "age")
                        .param("sortOrder", "asc")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[1].name").value("Alice"));
    }
}
