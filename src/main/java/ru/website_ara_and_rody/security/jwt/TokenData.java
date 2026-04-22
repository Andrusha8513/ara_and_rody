package ru.website_ara_and_rody.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.website_ara_and_rody.entity.Role;

import java.util.Set;

public record TokenData(Long id,
                        String email,
                        String password,
                        Set<Role> roles,
                        Boolean isEnabled) {

}
