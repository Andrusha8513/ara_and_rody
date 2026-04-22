package ru.website_ara_and_rody.mapper;

import org.mapstruct.Mapper;
import ru.website_ara_and_rody.dto.CommentDto;
import ru.website_ara_and_rody.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDto commentDto);
}
