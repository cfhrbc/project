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
import ru.kata.spring.boot_security.demo.dto.SocialMediaRequestDto;
import ru.kata.spring.boot_security.demo.dto.SocialMediaResponseDto;
import ru.kata.spring.boot_security.demo.service.SocialMediaService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialMediaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SocialMediaService socialMediaService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @Test
    void testCreateSocialMedia() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new SocialMediaRequestDto();
        input.setPlatform("Telegram");
        input.setUsername("user123");
        input.setUrl("https://t.me/user123");

        var saved = new SocialMediaResponseDto();
        saved.setId(1L);
        saved.setPlatform("Telegram");
        saved.setUsername("user123");
        saved.setUrl("https://t.me/user123");
        saved.setUserId(1L);

        when(socialMediaService.create(eq(1L), any(SocialMediaRequestDto.class))).thenReturn(saved);

        mockMvc.perform(post("/social-media/social/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.platform").value("Telegram"))
                .andExpect(jsonPath("$.username").value("user123"));
    }

    @Test
    void testUpdateSocialMedia() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var input = new SocialMediaRequestDto();
        input.setPlatform("Telegram");
        input.setUsername("updated_user");
        input.setUrl("https://t.me/updated_user");

        var updated = new SocialMediaResponseDto();
        updated.setId(1L);
        updated.setPlatform("Telegram");
        updated.setUsername("updated_user");
        updated.setUrl("https://t.me/updated_user");
        updated.setUserId(1L);

        when(socialMediaService.update(eq(1L), any(SocialMediaRequestDto.class))).thenReturn(updated);

        mockMvc.perform(put("/social-media/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updated_user"));
    }

    @Test
    void testDeleteSocialMedia() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        doNothing().when(socialMediaService).delete(1L);

        mockMvc.perform(delete("/social-media/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetSocialMediaById() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var dto = new SocialMediaResponseDto();
        dto.setId(1L);
        dto.setPlatform("Instagram");
        dto.setUsername("insta_user");
        dto.setUrl("https://instagram.com/insta_user");
        dto.setUserId(1L);

        when(socialMediaService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/social-media/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.platform").value("Instagram"))
                .andExpect(jsonPath("$.username").value("insta_user"));
    }

    @Test
    void testGetAllSocialMediaByUserId() throws Exception {
        UtilsTest.mockAdminAuthentication("admin", jwtTokenProvider, userDetailsService);

        var dto1 = new SocialMediaResponseDto();
        dto1.setId(1L);
        dto1.setPlatform("Telegram");
        dto1.setUsername("user1");
        dto1.setUrl("https://t.me/user1");
        dto1.setUserId(1L);

        var dto2 = new SocialMediaResponseDto();
        dto2.setId(2L);
        dto2.setPlatform("Instagram");
        dto2.setUsername("user2");
        dto2.setUrl("https://instagram.com/user2");
        dto2.setUserId(1L);

        when(socialMediaService.getAllByUserId(1L)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/social-media/user/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].platform").value("Telegram"))
                .andExpect(jsonPath("$[1].platform").value("Instagram"));
    }
}
