package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.WorkDto;

import java.util.Optional;

public interface WorkService {

    WorkDto findById(Long id);

    WorkDto create(Long userId, WorkDto dto);

    WorkDto update(Long id, WorkDto workDto);

    void deleteById(Long id);

    Optional<WorkDto> findByUserId(Long userId);
}
