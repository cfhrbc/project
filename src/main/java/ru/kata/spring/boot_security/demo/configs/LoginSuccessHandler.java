package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String redirectUrl = "/";

        // Проверяем, является ли пользователь администратором
        if (hasRole("ROLE_ADMIN", userDetails)) {
            redirectUrl = "/admin";
        }
        // Проверяем, является ли пользователь обычным пользователем
        else if (hasRole("ROLE_USER", userDetails)) {
            redirectUrl = "/user";
        }

        response.sendRedirect(redirectUrl);
    }

    // Вспомогательный метод для проверки роли
    private boolean hasRole(String role, UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }
}

