package ru.kata.spring.boot_security.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        var passwordEncoder = new BCryptPasswordEncoder();
        var hashedPassword = passwordEncoder.encode("ADMIN");
        System.out.println(hashedPassword);
    }
}
