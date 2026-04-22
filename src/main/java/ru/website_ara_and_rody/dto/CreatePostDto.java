package ru.website_ara_and_rody.dto;

public record CreatePostDto(Long user_id,
                            String title,
                            String text) {
}
