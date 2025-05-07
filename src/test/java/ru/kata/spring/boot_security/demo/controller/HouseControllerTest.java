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
import ru.kata.spring.boot_security.demo.dto.HouseDto;
import ru.kata.spring.boot_security.demo.service.HouseService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HouseService houseService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @Test
    void testCreateHouse() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = HouseDto.builder()
                .address("Москва, ул. Ленина, 10")
                .area(120.5)
                .constructionYear(2000)
                .userId(1L)
                .build();

        var saved = HouseDto.builder()
                .id(1L)
                .address("Москва, ул. Ленина, 10")
                .area(120.5)
                .constructionYear(2000)
                .userId(1L)
                .build();

        when(houseService.create(eq(1L), any(HouseDto.class))).thenReturn(saved);

        mockMvc.perform(post("/houses/user/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("Москва, ул. Ленина, 10"))
                .andExpect(jsonPath("$.area").value(120.5))
                .andExpect(jsonPath("$.constructionYear").value(2000));
    }

    @Test
    void testUpdateHouse() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var update = HouseDto.builder()
                .address("Москва, ул. Пушкина, 15")
                .area(130.0)
                .constructionYear(2010)
                .userId(1L)
                .build();

        var updated = HouseDto.builder()
                .id(1L)
                .address("Москва, ул. Пушкина, 15")
                .area(130.0)
                .constructionYear(2010)
                .userId(1L)
                .build();

        when(houseService.update(eq(1L), any(HouseDto.class))).thenReturn(updated);

        mockMvc.perform(put("/houses/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Москва, ул. Пушкина, 15"))
                .andExpect(jsonPath("$.area").value(130.0))
                .andExpect(jsonPath("$.constructionYear").value(2010));
    }

    @Test
    void testDeleteHouse() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        Mockito.doNothing().when(houseService).delete(1L);

        mockMvc.perform(delete("/houses/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetHouseById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var house = HouseDto.builder()
                .id(1L)
                .address("Москва, ул. Ленина, 10")
                .area(120.5)
                .constructionYear(2000)
                .userId(1L)
                .build();

        when(houseService.getById(1L)).thenReturn(house);

        mockMvc.perform(get("/houses/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Москва, ул. Ленина, 10"))
                .andExpect(jsonPath("$.area").value(120.5))
                .andExpect(jsonPath("$.constructionYear").value(2000));
    }

    @Test
    void testGetAllUserHouses() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var houses = List.of(
                HouseDto.builder()
                        .id(1L)
                        .address("Москва, ул. Ленина, 10")
                        .area(120.5)
                        .constructionYear(2000)
                        .userId(1L)
                        .build(),
                HouseDto.builder()
                        .id(2L)
                        .address("Санкт-Петербург, ул. Пушкина, 5")
                        .area(95.0)
                        .constructionYear(1998)
                        .userId(1L)
                        .build()
        );

        when(houseService.getAllByUserId(1L)).thenReturn(houses);

        mockMvc.perform(get("/houses/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Москва, ул. Ленина, 10"))
                .andExpect(jsonPath("$[1].address").value("Санкт-Петербург, ул. Пушкина, 5"));
    }
}
