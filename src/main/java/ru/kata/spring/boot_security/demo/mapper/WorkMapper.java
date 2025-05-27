package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.dto.WorkDto;
import ru.kata.spring.boot_security.demo.model.Work;

import static ru.kata.spring.boot_security.demo.constants.MapStructConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface WorkMapper {

    WorkDto toDto(Work work);

    Work toEntity(WorkDto workDto);
}
