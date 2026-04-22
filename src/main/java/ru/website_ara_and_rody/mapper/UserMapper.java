package ru.website_ara_and_rody.mapper;

import org.mapstruct.Mapper;
import ru.website_ara_and_rody.dto.MeDto;
import ru.website_ara_and_rody.dto.UserRegistrationDto;
import ru.website_ara_and_rody.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserRegistrationDto userRegistrationDto);
    MeDto toDto(Users users);
}
