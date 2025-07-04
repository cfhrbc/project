package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;

import java.util.List;

public interface EducationService {

    EducationResponseDto create(Long usersId, EducationRequestDto dto);

    EducationResponseDto update(Long id, EducationRequestDto dto);

    void delete(Long id);

    List<EducationResponseDto> getAllByUserId(Long usersId);

    EducationResponseDto getById(Long id);
}
