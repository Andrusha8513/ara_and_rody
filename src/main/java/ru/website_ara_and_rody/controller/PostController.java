package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.website_ara_and_rody.dto.CreatePostDto;
import ru.website_ara_and_rody.dto.PostDto;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.security.CustomUserDetails;
import ru.website_ara_and_rody.service.PostService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getALLPost() {
        try {
            List<PostDto> postDtoList = postService.getALLPost();
            return ResponseEntity.ok(postDtoList);
        } catch (RuntimeException e) {
            log.error("Ошибка с постами ", e);
            throw new IllegalArgumentException("Ошибка с постами" + e);
        }
    }

    @PostMapping("/creatPost")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestPart("createPostDto") CreatePostDto createPostDto,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        try {
            Post post = postService.createPost(customUserDetails.getId(), createPostDto, files);
            return ResponseEntity.ok(post);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("update-title-post/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateTitlePost(@PathVariable Long postId,
                                             @RequestParam String newTitle) {
        try {
            postService.updateTitlePost(postId, newTitle);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка при обновление поста ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("update-text-post/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateTextPost(@PathVariable Long postId,
                                            @RequestParam String newText) {
        try {
            postService.updateTextPost(postId, newText);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка при обновление поста ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка при удаление  поста ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}