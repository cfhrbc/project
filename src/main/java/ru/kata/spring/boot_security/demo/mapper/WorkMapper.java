package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.model.Work;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkMapper {

    WorkDto toDto(Work work);

    Work toEntity(WorkDto workDto);
}
