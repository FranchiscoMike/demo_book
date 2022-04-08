package uz.pdp.demo_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo_book.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);
}