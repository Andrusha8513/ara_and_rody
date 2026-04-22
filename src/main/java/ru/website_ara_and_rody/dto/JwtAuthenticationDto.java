package ru.website_ara_and_rody.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {
    public String token;
    private String refreshToken;
}
