package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.CarReqDto;
import ru.kata.spring.boot_security.demo.dto.CarResDto;

import java.util.List;

public interface CarService {

    CarResDto getCarById(Long id);

    CarResDto createCar(CarReqDto carReqDto);

    CarResDto updateCar(Long id, CarReqDto carReqDto);

    void deleteCar(Long id);

    List<CarResDto> getAllByUserId(Long userId);
}
