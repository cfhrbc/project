package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.FamilyDto;

import java.util.List;

public interface FamilyService {

    FamilyDto create(Long userId, FamilyDto dto);

    FamilyDto update(Long id, FamilyDto dto);

    void delete(Long id);

    List<FamilyDto> getAllByUserId(Long userId);

    FamilyDto getById(Long id);
}
