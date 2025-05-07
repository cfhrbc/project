package ru.kata.spring.boot_security.demo.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kata.spring.boot_security.demo.dto.CarReqDto;
import ru.kata.spring.boot_security.demo.dto.CarResDto;
import ru.kata.spring.boot_security.demo.model.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(source = "owner.username", target = "ownerName")
    CarResDto toResDto(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Car toEntity(CarReqDto carReqDto);
}
