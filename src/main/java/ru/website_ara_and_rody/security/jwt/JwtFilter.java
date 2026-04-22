package ru.website_ara_and_rody.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.website_ara_and_rody.security.CustomUserDetails;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull  HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if(token != null && jwtService.validateToken(token)){
            log.info("Токен получен: {}" , token);

            TokenData tokenData = jwtService.extractTokenData(token);
            List<GrantedAuthority> authority = tokenData.roles().stream().map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toUnmodifiableList());

            UserDetails userDetails = new CustomUserDetails(
                    tokenData.id() ,
                    tokenData.email(),
                    "",
                    authority
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails , null  , authority);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Для пользователя установлена аутентификация: {}", tokenData.email());

        }
        filterChain.doFilter(request , response);

    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearToken != null && bearToken.startsWith("Bearer ")){
            return bearToken.substring(7);
        }
        return null;
    }
}
