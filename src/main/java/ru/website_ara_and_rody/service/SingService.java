package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.website_ara_and_rody.dto.JwtAuthenticationDto;
import ru.website_ara_and_rody.dto.UserCredentialsDto;
import ru.website_ara_and_rody.dto.UserRegistrationDto;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.repositoty.UserRepository;
import ru.website_ara_and_rody.security.jwt.JwtService;
import ru.website_ara_and_rody.security.jwt.TokenData;

import javax.naming.AuthenticationException;


@Service
@RequiredArgsConstructor
public class SingService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Users users = findByCredentials(userCredentialsDto);
        TokenData tokenData = new TokenData(
                users.getId(),
                users.getEmail(),
                null,
                users.getRoles(),
                users.getEnable());

        if (users.getEnable() == true && jwtService.validateToken(users.getRefreshToken())) {
            return jwtService.refreshBaseToken(tokenData , users.getRefreshToken());
        }

        if (users.getEnable()) {
            JwtAuthenticationDto jwtAuthenticationDto = jwtService.generateAuthToken(tokenData);
            users.setRefreshToken(jwtAuthenticationDto.getRefreshToken());
            userRepository.save(users);
            return jwtAuthenticationDto;
        } else {
            throw new IllegalArgumentException("Пользователь не подтвердил почту!");
        }
    }

    private Users findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Users users = userRepository.findByEmail(userCredentialsDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с такой почтй не найден"));

        if (passwordEncoder.matches(userCredentialsDto.password(), users.getPassword())) {
            return users;
        }
        throw new AuthenticationException("Почта или пароль неверные!");
    }
}
