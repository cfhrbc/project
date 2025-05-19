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
import ru.kata.spring.boot_security.demo.dto.CarReqDto;
import ru.kata.spring.boot_security.demo.dto.CarResDto;
import ru.kata.spring.boot_security.demo.service.CarService;
import ru.kata.spring.boot_security.demo.service.UserService;

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
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private CarService carService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testGetCarById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var car = new CarResDto(1L, "Toyota", "Camry", "Black", 2025, "Ivan Petrov");

        when(carService.getCarById(1L)).thenReturn(car);

        mockMvc.perform(get("/cars/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void testCreateCar() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new CarReqDto("Toyota", "Camry", "Black", 2025, 1L);
        var saved = new CarResDto(1L, "Toyota", "Camry", "Black", 2025, "Ivan Petrov");

        when(carService.createCar(any(CarReqDto.class))).thenReturn(saved);

        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void testUpdateCar() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new CarReqDto("Toyota", "Camry", "Black", 2025, 1L);
        var updated = new CarResDto(1L, "Toyota", "Camry", "Black", 2025, "Ivan Petrov");

        when(carService.updateCar(eq(1L), any(CarReqDto.class))).thenReturn(updated);

        mockMvc.perform(put("/cars/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void testDeleteCar() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(delete("/cars/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllCarsByUserId() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        Long userId = 1L;

        var carList = List.of(
                new CarResDto(1L, "Toyota", "Camry", "Black", 2025, "Ivan Petrov"),
                new CarResDto(2L, "BMW", "X5", "White", 2023, "Ivan Petrov")
        );

        when(carService.getAllByUserId(userId)).thenReturn(carList);

        mockMvc.perform(get("/cars/user/{userId}", userId)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[1].model").value("X5"));
    }
}
