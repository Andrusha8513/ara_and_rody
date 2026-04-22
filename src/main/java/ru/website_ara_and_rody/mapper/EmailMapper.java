package ru.website_ara_and_rody.mapper;

import org.mapstruct.Mapper;
import ru.website_ara_and_rody.dto.EmailRequestDto;
import ru.website_ara_and_rody.entity.Users;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    EmailRequestDto toDto(Users users);
}
