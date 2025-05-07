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
import ru.kata.spring.boot_security.demo.dto.FamilyDto;
import ru.kata.spring.boot_security.demo.service.FamilyService;
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
public class FamilyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FamilyService familyService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @Test
    void testGetFamiliesByUserId() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var family = FamilyDto.builder()
                .id(1L)
                .relation("Отец")
                .name("Иван")
                .age(50)
                .phoneNumber("+79991234567")
                .userId(1L)
                .build();

        when(familyService.getAllByUserId(1L)).thenReturn(List.of(family));

        mockMvc.perform(get("/families/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Иван"));
    }

    @Test
    void testGetFamilyById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var family = FamilyDto.builder()
                .id(1L)
                .relation("Мать")
                .name("Анна")
                .age(45)
                .phoneNumber("+79991234567")
                .userId(2L)
                .build();

        when(familyService.getById(1L)).thenReturn(family);

        mockMvc.perform(get("/families/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Анна"));
    }

    @Test
    void testCreateFamily() throws Exception {

        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = FamilyDto.builder()
                .relation("Брат")
                .name("Алексей")
                .age(20)
                .phoneNumber("+79998887766")
                .userId(1L)
                .build();

        var saved = FamilyDto.builder()
                .id(1L)
                .relation("Брат")
                .name("Алексей")
                .age(20)
                .phoneNumber("+79998887766")
                .userId(1L)
                .build();

        when(familyService.create(eq(1L), any(FamilyDto.class))).thenReturn(saved);

        mockMvc.perform(post("/families/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Алексей"))
                .andExpect(jsonPath("$.relation").value("Брат"))
                .andExpect(jsonPath("$.phoneNumber").value("+79998887766"));
    }

    @Test
    void testUpdateFamily() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var update = FamilyDto.builder()
                .relation("Сестра")
                .name("Ольга")
                .age(30)
                .phoneNumber("+79991112233")
                .userId(2L)
                .build();

        var updated = FamilyDto.builder()
                .id(1L)
                .relation("Сестра")
                .name("Ольга")
                .age(30)
                .phoneNumber("+79991112233")
                .userId(2L)
                .build();

        when(familyService.update(eq(1L), any(FamilyDto.class))).thenReturn(updated);

        mockMvc.perform(put("/families/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ольга"));
    }

    @Test
    void testDeleteFamily() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        Mockito.doNothing().when(familyService).delete(1L);

        mockMvc.perform(delete("/families/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }
}
