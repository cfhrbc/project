package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.model.Family;
import ru.kata.spring.boot_security.demo.dto.FamilyDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FamilyMapper {

    FamilyDto toDto(Family family);

    Family toEntity(FamilyDto dto);

    List<FamilyDto> toDtoList(List<Family> families);
}
