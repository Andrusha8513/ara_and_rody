package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.website_ara_and_rody.dto.MeDto;
import ru.website_ara_and_rody.dto.UserRegistrationDto;
import ru.website_ara_and_rody.security.CustomUserDetails;
import ru.website_ara_and_rody.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDto userRegistrationDto){
        try {
            userService.registration(userRegistrationDto);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<?> confirmRegistration(@RequestParam String code){
        try {
            userService.confirmRegistration(code);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<MeDto> me(@AuthenticationPrincipal CustomUserDetails customUserDetails){
           MeDto me = userService.me(customUserDetails.getId());
           return ResponseEntity.ok(me);
    }
}
