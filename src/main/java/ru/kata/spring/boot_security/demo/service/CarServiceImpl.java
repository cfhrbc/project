package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.CarReqDto;
import ru.kata.spring.boot_security.demo.dto.CarResDto;
import ru.kata.spring.boot_security.demo.exception.CarNotFoundException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.mapper.CarMapper;
import ru.kata.spring.boot_security.demo.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserService userService;
    private final CarMapper carMapper;

    @Override
    public CarResDto getCarById(Long id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден"));
        return carMapper.toResDto(car);
    }

    @Override
    public CarResDto createCar(CarReqDto carReqDto) {
        var car = carMapper.toEntity(carReqDto);

        var owner = userService.findUserEntityById(carReqDto.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + carReqDto.getOwnerId() + " не найден"));

        car.setOwner(owner);
        var savedCar = carRepository.save(car);

        return carMapper.toResDto(savedCar);
    }

    @Override
    public CarResDto updateCar(Long id, CarReqDto carReqDto) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Машина с id " + id + " не найдена"));

        car.setBrand(carReqDto.getBrand());
        car.setModel(carReqDto.getModel());
        car.setColor(carReqDto.getColor());
        car.setYear(carReqDto.getYear());

        if (carReqDto.getOwnerId() != null) {
            var owner = userService.findUserEntityById(carReqDto.getOwnerId())
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + carReqDto.getOwnerId() + " не найден"));
            car.setOwner(owner);
        }
        var updatedCar = carRepository.save(car);
        return carMapper.toResDto(updatedCar);
    }

    @Override
    public List<CarResDto> getAllByUserId(Long ownerId) {
        return carRepository.findAllByOwnerId(ownerId).stream()
                .map(carMapper::toResDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException("Машина с id " + id + " не найдена");
        }
        carRepository.deleteById(id);
    }
}