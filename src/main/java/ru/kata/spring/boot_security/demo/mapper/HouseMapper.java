package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.model.House;
import ru.kata.spring.boot_security.demo.dto.HouseDto;

import java.util.List;

import static ru.kata.spring.boot_security.demo.constants.MapStructConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface HouseMapper {

    House toEntity(HouseDto dto);

    HouseDto toDto(House entity);

    List<HouseDto> toDtoList(List<House> entities);
}
