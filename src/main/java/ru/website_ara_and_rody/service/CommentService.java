package ru.website_ara_and_rody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.website_ara_and_rody.dto.CommentDto;
import ru.website_ara_and_rody.entity.Comment;
import ru.website_ara_and_rody.entity.Post;
import ru.website_ara_and_rody.mapper.CommentMapper;
import ru.website_ara_and_rody.repositoty.CommentRepository;
import ru.website_ara_and_rody.repositoty.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(Long id , CommentDto commentDto){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Поста таким id= " + id + " не найден" ));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setPost(post);
        post.getComments().add(comment);
        commentRepository.save(comment);
    }

}
