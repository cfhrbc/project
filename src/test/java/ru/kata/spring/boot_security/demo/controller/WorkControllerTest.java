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
import ru.kata.spring.boot_security.demo.dto.WorkRequestDto;
import ru.kata.spring.boot_security.demo.dto.WorkResponseDto;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.WorkService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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

        var request = new WorkRequestDto();
        request.setCompany("Company");
        request.setPosition("Developer");
        request.setStartDate("2022-01-01");
        request.setEndDate("2023-01-01");

        var response = new WorkResponseDto();
        response.setId(1L);
        response.setCompany("Company");
        response.setPosition("Developer");
        response.setStartDate("2022-01-01");
        response.setEndDate("2023-01-01");
        response.setUserId(1L);

        when(workService.create(eq(1L), any(WorkRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/work/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.company").value("Company"))
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    @Test
    void testUpdateWork() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var request = new WorkRequestDto();
        request.setCompany("NewCompany");
        request.setPosition("Lead Developer");
        request.setStartDate("2020-01-01");
        request.setEndDate("2021-12-31");

        var response = new WorkResponseDto();
        response.setId(1L);
        response.setCompany("NewCompany");
        response.setPosition("Lead Developer");
        response.setStartDate("2020-01-01");
        response.setEndDate("2021-12-31");
        response.setUserId(1L);

        when(workService.update(eq(1L), any(WorkRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/work/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("NewCompany"))
                .andExpect(jsonPath("$.position").value("Lead Developer"));
    }

    @Test
    void testDeleteWork() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        doNothing().when(workService).delete(1L);

        mockMvc.perform(delete("/work/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetWorkById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var response = new WorkResponseDto();
        response.setId(1L);
        response.setCompany("Company");
        response.setPosition("Developer");
        response.setStartDate("2022-01-01");
        response.setEndDate("2023-01-01");
        response.setUserId(1L);

        when(workService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/work/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Company"));
    }

    @Test
    void testGetAllWorkByUserId() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var response1 = new WorkResponseDto();
        response1.setId(1L);
        response1.setCompany("Company1");
        response1.setPosition("Dev1");
        response1.setStartDate("2020-01-01");
        response1.setEndDate("2021-01-01");
        response1.setUserId(1L);

        var response2 = new WorkResponseDto();
        response2.setId(2L);
        response2.setCompany("Company2");
        response2.setPosition("Dev2");
        response2.setStartDate("2021-02-01");
        response2.setEndDate("2022-02-01");
        response2.setUserId(1L);

        when(workService.getAllByUserId(1L)).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/work/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].company").value("Company1"))
                .andExpect(jsonPath("$[1].company").value("Company2"));
    }
}
