package ru.website_ara_and_rody.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.website_ara_and_rody.entity.Comment;

@Service
public class Owner_Comment {

    public void checkAccess(Comment comment){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !comment.getId().equals(currentUserId)){
            throw new RuntimeException("Отказано в доступе! Вы не являетесь владельцем этого ПВЗ.");
        }
    }
}
