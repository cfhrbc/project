package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.configs.JwtTokenProvider;

import static org.mockito.Mockito.when;

public class UtilsTest {

    public static void mockAdminAuthentication(String username,
                                               JwtTokenProvider jwtTokenProvider,
                                               UserDetailsService userDetailsService) {
        when(jwtTokenProvider.validateToken("token")).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn(username);

        var adminUser = User.withUsername(username)
                .password("admin")
                .authorities("ROLE_ADMIN")
                .build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(adminUser);
    }
}
