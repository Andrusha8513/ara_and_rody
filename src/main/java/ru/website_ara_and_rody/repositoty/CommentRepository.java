package ru.website_ara_and_rody.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.website_ara_and_rody.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment , Long> {
}
