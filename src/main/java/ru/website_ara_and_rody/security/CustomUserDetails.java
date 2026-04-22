package ru.website_ara_and_rody.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.website_ara_and_rody.entity.Users;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final Long id;

public CustomUserDetails (Long id , String username  , String password , boolean enabled  , boolean accountNonLocked , Collection<? extends GrantedAuthority> authorities){
    super(username, password, enabled, true,true, accountNonLocked, authorities);
    this.id = id;
}

    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }
}
