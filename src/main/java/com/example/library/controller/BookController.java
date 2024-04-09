package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.entity.State;
import com.example.library.exceptionHandling.exception.BadRequestException;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> list = bookService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        Book book = bookService.findById(id);
        if (book == null) {
            throw new NotFoundException("Book with id:" + id + " does not " +
                    "exist");
        }
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Book> create(@Valid @RequestBody BookDTO bookDTO) {

        if (bookDTO.getPublicationYear().isAfter(Year.now())) {
            throw new BadRequestException("Publication year cannot be after " +
                    "current year");
        }

        Book book = Book.builder().
                title(bookDTO.getTitle()).
                author(bookDTO.getAuthor()).
                publicationYear(bookDTO.getPublicationYear()).
                isbn(bookDTO.getIsbn()).
                state(State.AVAILABLE).
                build();
        return ResponseEntity.ok(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id,
                                           @RequestBody BookDTO bookDTO) {

        if (bookDTO.getPublicationYear().isAfter(Year.now())) {
            throw new BadRequestException("Publication year cannot be after " +
                    "current year");
        }

        Book dbBook = bookService.findById(id);
        Book book = Book.builder().
                id(id).
                title(bookDTO.getTitle()).
                author(bookDTO.getAuthor()).
                publicationYear(bookDTO.getPublicationYear()).
                isbn(bookDTO.getIsbn()).
                state(dbBook.getState()).
                build();
        return ResponseEntity.ok(bookService.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
