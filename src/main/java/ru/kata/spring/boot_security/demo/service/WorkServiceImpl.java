package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.exception.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.WorkMapper;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Work;
import ru.kata.spring.boot_security.demo.repository.WorkRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final UserService userService;
    private final WorkMapper workMapper;

    public WorkDto create(Long userId, WorkDto dto) {
        User user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + userId + " не найден"));

        Work work = workMapper.toEntity(dto);
        work.getUsers().add(user);
        user.setWork(work);

        Work savedWork = workRepository.save(work);
        return workMapper.toDto(savedWork);
    }

    @Override
    public WorkDto update(Long id, WorkDto dto) {
        var existing = workRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Работа не найдена"));

        existing.setCompany(dto.getCompany());
        existing.setPosition(dto.getPosition());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());

        var updated = workRepository.save(existing);
        return workMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        workRepository.deleteById(id);
    }

    @Override
    public WorkDto findById(Long id) {
        return workMapper.toDto(
                workRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Work not found")));
    }

    @Override
    public Optional<WorkDto> findByUserId(Long userId) {
        return workRepository.findWorkByUsersId(userId).map(workMapper::toDto);
    }
}
