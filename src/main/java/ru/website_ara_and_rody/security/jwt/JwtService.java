package ru.website_ara_and_rody.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.website_ara_and_rody.dto.JwtAuthenticationDto;
import ru.website_ara_and_rody.entity.Role;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {
    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(30);
    private static final long REFRESH_RENEW_THRESHOLD_DAYS = 7;

    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_IS_ENABLED = "isEnabled";


    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtAuthenticationDto generateAuthToken(TokenData tokenData){
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(buildAccessToken(tokenData));
        jwtAuthenticationDto.setRefreshToken(buildRefreshToken(tokenData.email()));
        return jwtAuthenticationDto;
    }
    public JwtAuthenticationDto refreshBaseToken(TokenData tokenData , String refreshToken){
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(buildAccessToken(tokenData));
        jwtAuthenticationDto.setRefreshToken(refreshToken);
        return jwtAuthenticationDto;
    }

    private String buildAccessToken(TokenData tokenData){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(tokenData.email())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ACCESS_TOKEN_DURATION)))
                .claim(CLAIM_USER_ID , tokenData.id())
                .claim(CLAIM_ROLES , tokenData.roles().stream()
                        .map(Role::name).collect(Collectors.toList()))
                .signWith(signingKey())
                .compact();
    }
    private String buildRefreshToken(String email){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(REFRESH_TOKEN_DURATION)))
                .signWith(signingKey())
                .compact();
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public TokenData extractTokenData(String token){
        Claims claims = parseClaims(token);
        return new TokenData(
                claims.get(CLAIM_USER_ID , Long.class),
                claims.getSubject(),
                null,
                extractRoles(claims),
                claims.get(CLAIM_IS_ENABLED , Boolean.class)
        );
    }


    private Set<Role> extractRoles(Claims claims) {
        List<String> role = claims.get(CLAIM_ROLES , List.class);
        if(role == null || role.isEmpty()){
            throw new IllegalArgumentException("Роль зарегистрированного пользователя не может быть пустой");
        }
        return role.stream()
                .map(Role::valueOf)
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT истёк: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e)  {
            log.warn("JWT не поддерживается: {}", e.getMessage());
        }
        catch (MalformedJwtException e)    {
            log.warn("JWT повреждён: {}", e.getMessage());
        }
        catch (SignatureException e)       {
            log.warn("JWT неверная подпись: {}", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            log.warn("JWT null: {}", e.getMessage());
        }
        return false;
    }


    private SecretKey signingKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
