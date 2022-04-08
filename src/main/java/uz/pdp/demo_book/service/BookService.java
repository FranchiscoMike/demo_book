package uz.pdp.demo_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.demo_book.entity.Book;
import uz.pdp.demo_book.payload.ApiResponse;
import uz.pdp.demo_book.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public ApiResponse list() {
        List<Book> all = bookRepository.findAll();
        return new ApiResponse("all", true, all);
    }

    public ApiResponse add(Book book) {
        Optional<Book> optionalBook = bookRepository.findByName(book.getName());
        if (optionalBook.isPresent()) {
            return new ApiResponse("This is already added", false);
        }
        Book book1 = new Book();
        book1.setName(book.getName());
        Book save = bookRepository.save(book1);
        return new ApiResponse("Added", true, save);
    }

    public ApiResponse edit(Integer id, Book book) {

        Optional<Book> optionalBook1 = bookRepository.findByName(book.getName());
        if (optionalBook1.isPresent()) {
            return new ApiResponse("This is already added", false);
        }
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book editBook = optionalBook.get();
            editBook.setName(book.getName());
            Book save = bookRepository.save(editBook);
            return new ApiResponse("Book edited",true);
        }
        return new ApiResponse("something went wrong",false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
            return new ApiResponse("Book deleted",true);
        }
        return new ApiResponse("something went wrong",false);
    }

    public ApiResponse getOne(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return new ApiResponse("Book deleted",true,book);
        }
        return new ApiResponse("something went wrong",false);
    }
}
