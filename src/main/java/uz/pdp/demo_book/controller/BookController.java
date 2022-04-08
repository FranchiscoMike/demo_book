package uz.pdp.demo_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo_book.entity.Book;
import uz.pdp.demo_book.payload.ApiResponse;
import uz.pdp.demo_book.service.BookService;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    /**
     * SHOW ALL
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize(value = "permitAll()")
    public HttpEntity<?> list(){
        ApiResponse apiResponse = bookService.list();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * ADD ONE
     * @param book
     * @return
     */
    @PreAuthorize(value = "hasAnyRole(ADMIN,SUPER_ADMIN)")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody Book book){
        ApiResponse apiResponse = bookService.add(book);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201:409).body(apiResponse);
    }

    /**
     * edit one book BY ADMIN
     * @param id
     * @param book
     * @return
     */
    @PutMapping("/edit/{id}")
    @PreAuthorize(value = "hasRole(ADMIN)")
    public HttpEntity<?> edit(@PathVariable Integer id ,@RequestBody Book book){
        ApiResponse apiResponse = bookService.edit(id,book);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    /**
     * DELETE ONLY SUPER ADMIN
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(value = "hasRole(SUPER_ADMIN)")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = bookService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }

    /**
     * EVERYONE CAN SEE ONE BOOK  HAVE ROLE
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole(ADMIN,SUPER_ADMIN,USER)")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = bookService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200:409).body(apiResponse);
    }




}
