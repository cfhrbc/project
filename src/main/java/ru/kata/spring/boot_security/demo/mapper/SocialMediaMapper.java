package ru.kata.spring.boot_security.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kata.spring.boot_security.demo.dto.SocialMediaRequestDto;
import ru.kata.spring.boot_security.demo.dto.SocialMediaResponseDto;
import ru.kata.spring.boot_security.demo.model.SocialMedia;

import java.util.List;

import static ru.kata.spring.boot_security.demo.constants.MapStructConstants.SPRING;

@Mapper(componentModel = SPRING)
public interface SocialMediaMapper {

    @Mapping(target = "user", ignore = true)
    SocialMedia toEntity(SocialMediaRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    SocialMediaResponseDto toResponseDto(SocialMedia entity);

    @Mapping(source = "user.id", target = "userId")
    List<SocialMediaResponseDto> toResponseDtoList(List<SocialMedia> entities);
}
