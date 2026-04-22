package ru.website_ara_and_rody.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.website_ara_and_rody.dto.CreatePostDto;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(CreatePostDto createPostDto);
    @Mapping(source = "users.id", target = "user_id")
    PostDto toDto(Post post);
}
