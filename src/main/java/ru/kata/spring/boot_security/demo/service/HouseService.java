package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.HouseDto;

import java.util.List;

public interface HouseService {

    HouseDto create(Long userId, HouseDto dto);

    HouseDto update(Long id, HouseDto dto);

    void delete(Long id);

    HouseDto getById(Long id);

    List<HouseDto> getAllByUserId(Long userId);
}
