package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.model.Work;

@Mapper(componentModel = "spring")
public interface WorkMapper {

    WorkDto toDto(Work work);

    Work toEntity(WorkDto workDto);
}
