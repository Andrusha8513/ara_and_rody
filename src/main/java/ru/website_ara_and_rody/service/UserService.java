package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.website_ara_and_rody.dto.EmailRequestDto;
import ru.website_ara_and_rody.dto.JwtAuthenticationDto;
import ru.website_ara_and_rody.dto.UserRegistrationDto;
import ru.website_ara_and_rody.email.EmailService;
import ru.website_ara_and_rody.entity.Role;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.mapper.EmailMapper;
import ru.website_ara_and_rody.mapper.UserMapper;
import ru.website_ara_and_rody.repositoty.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final EmailMapper emailMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registration(UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByEmail((userRegistrationDto.email())).isPresent()) {
            throw new IllegalArgumentException("пользователь с такой почтой уже зарегистрирован!");
        }
        if (userRegistrationDto.password().length() < 8) {
            throw new IllegalArgumentException("Пароль не может быть короче 8 символов ");
        }

        Users users = userMapper.toEntity(userRegistrationDto);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        users.setConfirmationCode(code);
        users.setRoles(Set.of(Role.USER));

        EmailRequestDto emailRequestDto = emailMapper.toDto(users);
        emailService.sendConfirmationEmail(emailRequestDto);

        userRepository.save(users);
    }

    @Transactional
    public void confirmRegistration(String code) {
        Users users = userRepository.findByConfirmationCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        users.setConfirmationCode(null);
        users.setEnable(true);
        userRepository.save(users);
    }


}
