package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;
import ru.kata.spring.boot_security.demo.exception.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.EducationMapper;
import ru.kata.spring.boot_security.demo.model.Education;
import ru.kata.spring.boot_security.demo.repository.EducationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationServiceImpl implements EducationService {

    private final EducationRepository repository;
    private final EducationMapper mapper;
    private final UserService userService;

    @Override
    public EducationResponseDto create(Long userId, EducationRequestDto dto) {
        var user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        var entity = mapper.toEntity(dto);
        entity.setUser(user);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    public EducationResponseDto update(Long id, EducationRequestDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Образование не найдено"));

        existing.setInstitution(dto.getInstitution());
        existing.setDegree(dto.getDegree());
        existing.setStartYear(dto.getStartYear());
        existing.setEndYear(dto.getEndYear());

        var updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EducationResponseDto> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponseDto getById(Long id) {
        Education education = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Образование не найдено"));
        return mapper.toDto(education);
    }
}
