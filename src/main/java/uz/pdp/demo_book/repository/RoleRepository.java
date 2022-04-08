package uz.pdp.demo_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo_book.entity.Role;
import uz.pdp.demo_book.entity.enums.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}