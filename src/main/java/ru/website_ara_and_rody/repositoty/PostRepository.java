package ru.website_ara_and_rody.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.website_ara_and_rody.entity.Post;

public interface PostRepository extends JpaRepository<Post , Long> {
}
