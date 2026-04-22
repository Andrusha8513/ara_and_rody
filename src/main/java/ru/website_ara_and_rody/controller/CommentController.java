package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.website_ara_and_rody.dto.CommentDto;
import ru.website_ara_and_rody.security.CustomUserDetails;
import ru.website_ara_and_rody.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId,
                                           @RequestBody CommentDto commentDto) {
        commentService.createComment(postId, commentDto);
        return ResponseEntity.ok().build();
    }
}
