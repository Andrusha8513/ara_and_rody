package ru.website_ara_and_rody.mapper;

import org.mapstruct.Mapper;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto postDto);
}
