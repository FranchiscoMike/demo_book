package uz.pdp.demo_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo_book.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);
}