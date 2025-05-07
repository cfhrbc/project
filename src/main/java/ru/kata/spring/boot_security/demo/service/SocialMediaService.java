package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.dto.SocialMediaRequestDto;
import ru.kata.spring.boot_security.demo.dto.SocialMediaResponseDto;

import java.util.List;

public interface SocialMediaService {

    SocialMediaResponseDto create(Long userId, SocialMediaRequestDto dto);

    SocialMediaResponseDto update(Long id, SocialMediaRequestDto dto);

    void delete(Long id);

    SocialMediaResponseDto getById(Long id);

    List<SocialMediaResponseDto> getAllByUserId(Long userId);
}
