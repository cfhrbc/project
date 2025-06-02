package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.model.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
