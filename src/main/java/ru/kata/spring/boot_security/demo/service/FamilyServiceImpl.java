package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exception.EntityNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.FamilyMapper;
import ru.kata.spring.boot_security.demo.dto.FamilyDto;
import ru.kata.spring.boot_security.demo.repository.FamilyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;
    private final FamilyMapper mapper;

    @Override
    public FamilyDto create(Long userId, FamilyDto dto) {
        var user = userService.findUserEntityById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        var family = mapper.toEntity(dto);
        family.setUser(user);
        return mapper.toDto(familyRepository.save(family));
    }

    @Override
    public FamilyDto update(Long id, FamilyDto dto) {
        var family = familyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Родственник не найден"));
        family.setRelation(dto.getRelation());
        family.setName(dto.getName());
        family.setAge(dto.getAge());
        family.setPhoneNumber(dto.getPhoneNumber());
        return mapper.toDto(familyRepository.save(family));
    }

    @Override
    public void delete(Long id) {
        familyRepository.deleteById(id);
    }

    @Override
    public List<FamilyDto> getAllByUserId(Long userId) {
        return mapper.toDtoList(familyRepository.findAllByUserId(userId));
    }

    @Override
    public FamilyDto getById(Long id) {
        return mapper.toDto(familyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Родственник не найден")));
    }
}
