package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kata.spring.boot_security.demo.dto.HouseDto;
import ru.kata.spring.boot_security.demo.model.House;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseMapper {

    House toEntity(HouseDto dto);

    HouseDto toDto(House entity);

    List<HouseDto> toDtoList(List<House> entities);
}
