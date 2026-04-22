package ru.website_ara_and_rody.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.website_ara_and_rody.entity.Image;

public interface ImageRepository extends JpaRepository<Image ,Long> {
}
