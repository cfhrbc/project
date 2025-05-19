package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ru.kata.spring.boot_security.demo.dto.EducationRequestDto;
import ru.kata.spring.boot_security.demo.dto.EducationResponseDto;
import ru.kata.spring.boot_security.demo.model.Education;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    @Mapping(target = "users", ignore = true) // users будут устанавливаться вручную в сервисе
    Education toEntity(EducationRequestDto dto);

    @Mapping(source = "users", target = "usersIds")
    EducationResponseDto toDto(Education education);

    default Set<Long> mapUsersToUserIds(Set<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
