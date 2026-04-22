package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.security.CustomUserDetails;
import ru.website_ara_and_rody.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/creatPost")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PostDto postDto){

       try {
           Post post = postService.createPost(customUserDetails.getId() , postDto);
           return ResponseEntity.ok(post);
       }catch (RuntimeException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

    }
}
