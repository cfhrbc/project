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
import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;
import ru.kata.spring.boot_security.demo.service.EducationService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EducationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EducationService educationService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @Test
    void testCreateEducation() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new EducationRequestDto();
        input.setInstitution("MIT");
        input.setDegree("Bachelor");
        input.setStartYear(2010);
        input.setEndYear(2014);

        var saved = new EducationResponseDto();
        saved.setId(1L);
        saved.setInstitution("MIT");
        saved.setDegree("Bachelor");
        saved.setStartYear(2010);
        saved.setEndYear(2014);
        saved.setUserId(1L);

        when(educationService.create(eq(1L), any(EducationRequestDto.class))).thenReturn(saved);

        mockMvc.perform(post("/education/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.institution").value("MIT"))
                .andExpect(jsonPath("$.degree").value("Bachelor"));
    }

    @Test
    void testUpdateEducation() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new EducationRequestDto();
        input.setInstitution("Stanford");
        input.setDegree("Master");
        input.setStartYear(2015);
        input.setEndYear(2017);

        var updated = new EducationResponseDto();
        updated.setId(1L);
        updated.setInstitution("Stanford");
        updated.setDegree("Master");
        updated.setStartYear(2015);
        updated.setEndYear(2017);
        updated.setUserId(1L);

        when(educationService.update(eq(1L), any(EducationRequestDto.class))).thenReturn(updated);

        mockMvc.perform(put("/education/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.institution").value("Stanford"))
                .andExpect(jsonPath("$.degree").value("Master"));
    }

    @Test
    void testGetEducationById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var education = new EducationResponseDto();
        education.setId(1L);
        education.setInstitution("Oxford");
        education.setDegree("PhD");
        education.setStartYear(2012);
        education.setEndYear(2016);
        education.setUserId(1L);

        when(educationService.getById(1L)).thenReturn(education);

        mockMvc.perform(get("/education/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.institution").value("Oxford"))
                .andExpect(jsonPath("$.degree").value("PhD"));
    }

    @Test
    void testGetAllEducationByUserId() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var edu1 = new EducationResponseDto();
        edu1.setId(1L);
        edu1.setInstitution("Oxford");
        edu1.setDegree("PhD");
        edu1.setStartYear(2012);
        edu1.setEndYear(2016);
        edu1.setUserId(1L);

        var edu2 = new EducationResponseDto();
        edu2.setId(2L);
        edu2.setInstitution("Cambridge");
        edu2.setDegree("Bachelor");
        edu2.setStartYear(2008);
        edu2.setEndYear(2012);
        edu2.setUserId(1L);

        when(educationService.getAllByUserId(1L)).thenReturn(List.of(edu1, edu2));

        mockMvc.perform(get("/education/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].institution").value("Oxford"))
                .andExpect(jsonPath("$[1].institution").value("Cambridge"));
    }

    @Test
    void testDeleteEducation() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        mockMvc.perform(delete("/education/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());

        verify(educationService, times(1)).delete(1L);
    }
}



