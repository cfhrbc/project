package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.SocialMediaRequestDto;
import ru.kata.spring.boot_security.demo.dto.SocialMediaResponseDto;
import ru.kata.spring.boot_security.demo.exception.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.SocialMediaMapper;
import ru.kata.spring.boot_security.demo.repository.SocialMediaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    private final UserService userService;
    private final SocialMediaRepository socialMediaRepository;
    private final SocialMediaMapper socialMediaMapper;

    @Override
    public SocialMediaResponseDto create(Long userId, SocialMediaRequestDto dto) {
        var user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        var entity = socialMediaMapper.toEntity(dto);
        entity.setUser(user);

        return socialMediaMapper.toResponseDto(socialMediaRepository.save(entity));
    }

    @Override
    public SocialMediaResponseDto update(Long id, SocialMediaRequestDto dto) {
        var existing = socialMediaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Соцсеть не найдена"));

        existing.setPlatform(dto.getPlatform());
        existing.setUsername(dto.getUsername());
        existing.setUrl(dto.getUrl());

        var updated = socialMediaRepository.save(existing);
        return socialMediaMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        socialMediaRepository.deleteById(id);
    }

    @Override
    public SocialMediaResponseDto getById(Long id) {
        var entity = socialMediaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Соцсеть не найдена"));
        return socialMediaMapper.toResponseDto(entity);
    }

    @Override
    public List<SocialMediaResponseDto> getAllByUserId(Long userId) {
        var entities = socialMediaRepository.findAllByUserId(userId);
        return socialMediaMapper.toResponseDtoList(entities);
    }

}
