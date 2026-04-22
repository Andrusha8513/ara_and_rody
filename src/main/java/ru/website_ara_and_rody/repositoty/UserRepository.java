package ru.website_ara_and_rody.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.website_ara_and_rody.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users , Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByConfirmationCode(String confirmationCode);
}
