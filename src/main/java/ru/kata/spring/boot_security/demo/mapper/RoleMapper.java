package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.dto.RoleDto;

import static ru.kata.spring.boot_security.demo.constants.MapStructConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
