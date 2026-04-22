package ru.website_ara_and_rody.dto;

public record UserRegistrationDto(
        String email,
        String password ,
        String name
) {
}
