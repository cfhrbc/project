package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kata.spring.boot_security.demo.dto.FamilyDto;
import ru.kata.spring.boot_security.demo.model.Family;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FamilyMapper {

    FamilyDto toDto(Family family);

    Family toEntity(FamilyDto dto);

    List<FamilyDto> toDtoList(List<Family> families);
}
