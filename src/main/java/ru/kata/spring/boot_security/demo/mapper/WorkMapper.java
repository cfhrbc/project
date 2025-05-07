package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.dto.WorkRequestDto;
import ru.kata.spring.boot_security.demo.dto.WorkResponseDto;
import ru.kata.spring.boot_security.demo.model.Work;

@Mapper(componentModel = "spring")
public interface WorkMapper {

    Work toEntity(WorkRequestDto dto);

    WorkResponseDto toResponseDto(Work entity);
}
