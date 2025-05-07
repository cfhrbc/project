package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.WorkRequestDto;
import ru.kata.spring.boot_security.demo.dto.WorkResponseDto;
import ru.kata.spring.boot_security.demo.exception.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.WorkMapper;
import ru.kata.spring.boot_security.demo.repository.WorkRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final UserService userService;
    private final WorkMapper workMapper;

    @Override
    public WorkResponseDto create(Long userId, WorkRequestDto dto) {
        var user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        var entity = workMapper.toEntity(dto);
        entity.setUser(user);

        return workMapper.toResponseDto(workRepository.save(entity));
    }

    @Override
    public WorkResponseDto update(Long id, WorkRequestDto dto) {
        var existing = workRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Работа не найдена"));

        existing.setCompany(dto.getCompany());
        existing.setPosition(dto.getPosition());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());

        var updated = workRepository.save(existing);
        return workMapper.toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        workRepository.deleteById(id);
    }

    @Override
    public WorkResponseDto getById(Long id) {
        return workMapper.toResponseDto(
                workRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Работа не найдена")));
    }

    @Override
    public List<WorkResponseDto> getAllByUserId(Long userId) {
        return workRepository.findAllByUserId(userId)
                .stream()
                .map(workMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
