package ru.kata.spring.boot_security.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.WorkService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkService workService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @Test
    void testCreateWork() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new WorkDto();
        input.setCompany("Google");
        input.setPosition("Developer");
        input.setStartDate("2020-01-01");
        input.setEndDate("2023-01-01");

        var saved = new WorkDto();
        saved.setId(1L);
        saved.setCompany("Google");
        saved.setPosition("Developer");
        saved.setStartDate("2020-01-01");
        saved.setEndDate("2023-01-01");

        when(workService.create(eq(1L), any(WorkDto.class))).thenReturn(saved);

        mockMvc.perform(post("/work/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    @Test
    void testUpdateWork() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new WorkDto();
        input.setCompany("Amazon");
        input.setPosition("Lead");
        input.setStartDate("2021-01-01");
        input.setEndDate("2024-01-01");

        var updated = new WorkDto();
        updated.setId(1L);
        updated.setCompany("Amazon");
        updated.setPosition("Lead");
        updated.setStartDate("2021-01-01");
        updated.setEndDate("2024-01-01");

        when(workService.update(eq(1L), any(WorkDto.class))).thenReturn(updated);

        mockMvc.perform(put("/work/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Amazon"))
                .andExpect(jsonPath("$.position").value("Lead"));
    }

    @Test
    void testDeleteWork() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        mockMvc.perform(delete("/work/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());

        verify(workService).deleteById(1L);
    }

    @Test
    void testGetWorkById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var dto = new WorkDto();
        dto.setId(1L);
        dto.setCompany("Apple");
        dto.setPosition("Manager");
        dto.setStartDate("2019-01-01");
        dto.setEndDate("2022-01-01");

        when(workService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/work/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Apple"))
                .andExpect(jsonPath("$.position").value("Manager"));
    }

    @Test
    void testGetWorkByUserId_Found() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var dto = new WorkDto();
        dto.setId(1L);
        dto.setCompany("Tesla");
        dto.setPosition("Engineer");
        dto.setStartDate("2018-01-01");
        dto.setEndDate("2021-01-01");

        when(workService.findByUserId(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/work/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Tesla"))
                .andExpect(jsonPath("$.position").value("Engineer"));
    }

    @Test
    void testGetWorkByUserId_NotFound() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        when(workService.findByUserId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/work/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNotFound());
    }
}
