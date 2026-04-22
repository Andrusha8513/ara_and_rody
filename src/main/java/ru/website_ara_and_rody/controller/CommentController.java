package ru.website_ara_and_rody.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.website_ara_and_rody.dto.CommentDto;
import ru.website_ara_and_rody.dto.RequestCommentDto;
import ru.website_ara_and_rody.security.CustomUserDetails;
import ru.website_ara_and_rody.service.CommentService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @PathVariable Long postId,
                                           @RequestBody CommentDto commentDto) {
        log.info("Запрос на создание комментария: postId={}, userId={}, text={}",
                postId, customUserDetails.getId(), commentDto.comment_text());
        try {
            commentService.createComment(postId, customUserDetails.getId(), commentDto);
            log.info("Комментарий успешно создан для postId={} пользователем {}", postId, customUserDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка при создании комментария для postId={}: {}", postId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-All-Comments-in-post/{postId}")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        log.info("Запрос на получение всех комментариев для postId={}", postId);
        try {
            List<RequestCommentDto> comments = commentService.getAllComments(postId);
            log.info("Найдено {} комментариев для postId={}", comments.size(), postId);
            return ResponseEntity.ok(comments);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении комментариев для postId={}: {}", postId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        log.info("Запрос на удаление комментария с id={}", commentId);
        try {
            commentService.deleteComment(commentId);
            log.info("Комментарий с id={} успешно удалён", commentId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка при удалении комментария с id={}: {}", commentId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
