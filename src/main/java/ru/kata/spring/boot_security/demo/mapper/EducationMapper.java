package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;
import ru.kata.spring.boot_security.demo.model.Education;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    Education toEntity(EducationRequestDto dto);

    EducationResponseDto toDto(Education education);
}
