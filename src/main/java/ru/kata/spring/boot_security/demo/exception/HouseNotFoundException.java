package ru.kata.spring.boot_security.demo.exception;

public class HouseNotFoundException extends RuntimeException {

    public HouseNotFoundException(String message) {
        super(message);
    }
}
