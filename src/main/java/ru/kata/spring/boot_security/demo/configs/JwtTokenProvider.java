package ru.kata.spring.boot_security.demo.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret; // Секретный ключ для подписи токенов, который берется из файла конфигурации.

    @Value("${jwt.expiration}")
    private long jwtExpiration; // Срок действия токена, также задается в конфигурации.

    // Генерация токена на основе данных аутентификации пользователя
    public String generateToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // Получаем список ролей пользователя и преобразуем их в строку.

        return Jwts.builder()
                .setSubject(authentication.getName()) // Задаем имя пользователя в качестве основного содержания токена.
                .claim("roles", roles) // Добавляем роли пользователя в токен.
                .setIssuedAt(new Date()) // Устанавливаем текущую дату в качестве времени выдачи токена.
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Устанавливаем срок действия.
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Подписываем токен с использованием секретного ключа.
                .compact(); // Компактируем данные в токен.
    }

    // Получение имени пользователя из токена
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // Проверка токена на валидность
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); // Если токен неверен, будет выброшено исключение.
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}



