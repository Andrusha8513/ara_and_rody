package ru.website_ara_and_rody.dto;

public record ImageDto(Long id,
                       String name ,
                       String originalFileName ,
                       Long size,
                       String contentType) {
}
