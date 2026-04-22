package ru.website_ara_and_rody.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.website_ara_and_rody.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment , Long> {
    List<Comment> findByPostId(Long postId);
}
