package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exception.HouseNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.HouseMapper;
import ru.kata.spring.boot_security.demo.model.House;
import ru.kata.spring.boot_security.demo.dto.HouseDto;
import ru.kata.spring.boot_security.demo.repository.HouseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UserService userService;
    private final HouseMapper houseMapper;

    @Override
    public HouseDto create(Long userId, HouseDto dto) {
        var user = userService.findUserEntityById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        var house = houseMapper.toEntity(dto);
        house.setUser(user);
        return houseMapper.toDto(houseRepository.save(house));
    }

    @Override
    public HouseDto update(Long id, HouseDto dto) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("Дом не найдем"));

        house.setAddress(dto.getAddress());
        house.setArea(dto.getArea());
        house.setConstructionYear(dto.getConstructionYear());
        return houseMapper.toDto(houseRepository.save(house));
    }

    @Override
    public void delete(Long id) {
        if (!houseRepository.existsById(id)) {
            throw new HouseNotFoundException("Дом не найден");
        }
        houseRepository.deleteById(id);
    }

    @Override
    public HouseDto getById(Long id) {
        return houseRepository.findById(id)
                .map(houseMapper::toDto)
                .orElseThrow(() -> new HouseNotFoundException("Дом не найден"));
    }

    @Override
    public List<HouseDto> getAllByUserId(Long userId) {
        return houseMapper.toDtoList(houseRepository.findAllByUserId(userId));
    }
}
