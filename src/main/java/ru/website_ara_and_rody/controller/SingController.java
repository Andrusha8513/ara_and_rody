package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.website_ara_and_rody.dto.JwtAuthenticationDto;
import ru.website_ara_and_rody.dto.UserCredentialsDto;
import ru.website_ara_and_rody.service.SingService;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/singIn")
public class SingController {
    private final SingService singService;

    @PostMapping("/auth")
    public ResponseEntity<JwtAuthenticationDto> singIn(@RequestBody UserCredentialsDto userCredentialsDto){
        try {
           JwtAuthenticationDto jwtAuthenticationDto =  singService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        }catch (AuthenticationException e){
            throw new RuntimeException("Аутентификация не удалась" + e.getMessage());
        }
    }
}
