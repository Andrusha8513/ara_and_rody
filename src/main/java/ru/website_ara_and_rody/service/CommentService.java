package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.website_ara_and_rody.dto.CommentDto;
import ru.website_ara_and_rody.dto.RequestCommentDto;
import ru.website_ara_and_rody.entity.Comment;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.entity.Users;
import ru.website_ara_and_rody.mapper.CommentMapper;
import ru.website_ara_and_rody.repositoty.CommentRepository;
import ru.website_ara_and_rody.repositoty.PostRepository;
import ru.website_ara_and_rody.repositoty.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(Long postId , Long userId, CommentDto commentDto){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Поста таким id= " + postId + " не найден" ));

        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с  таким id= " + userId + " не найдено" ));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setPost(post);
        comment.setUsers(users);
        post.getComments().add(comment);
        commentRepository.save(comment);
    }
    @Transactional(readOnly = true)
    public List<RequestCommentDto> getAllComments(Long postId){
return commentRepository.findByPostId(postId).stream()
        .map(commentMapper::toResponseDto)
        .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий с таким id " + id + " не найден!"));
        commentRepository.delete(comment);
    }

}
