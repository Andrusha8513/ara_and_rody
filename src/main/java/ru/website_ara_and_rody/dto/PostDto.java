package ru.website_ara_and_rody.dto;

import java.util.List;

public record PostDto(
        Long id,
        Long user_id,
        String title,
        String text,
        List<ImageDto> images) {
}
