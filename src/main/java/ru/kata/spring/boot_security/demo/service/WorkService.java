package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.WorkRequestDto;
import ru.kata.spring.boot_security.demo.dto.WorkResponseDto;

import java.util.List;

public interface WorkService {

    WorkResponseDto create(Long userId, WorkRequestDto dto);

    WorkResponseDto update(Long id, WorkRequestDto dto);

    void delete(Long id);

    WorkResponseDto getById(Long id);

    List<WorkResponseDto> getAllByUserId(Long userId);
}
