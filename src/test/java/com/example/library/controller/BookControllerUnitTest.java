package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.entity.State;
import com.example.library.exceptionHandling.exception.BadRequestException;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Year;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookControllerUnitTest {
    @MockBean
    private BookService bookService;

    @Test
    void givenNoBooks_whenFindAll_thenReturnEmptyList() {
        when(bookService.findAll()).thenReturn(new ArrayList<>());
        BookController bookController = new BookController(bookService);

        ResponseEntity<?> response = bookController.getAll();
        assertEquals(response.getBody(),new ArrayList<>());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void givenOneBook_whenFindById_thenReturnOneBook() {
        Book book = Book.builder().
                id(1).
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.of(2013)).
                state(State.BORROWED).
                isbn("5341348768").
                build();

        when(bookService.findById(1)).thenReturn(book);
        BookController bookController = new BookController(bookService);

        ResponseEntity<?> response = bookController.getById(1);
        assertEquals(response.getBody(),book);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void givenNoBooks_whenFindById_thenThrowNotFoundException() {
        when(bookService.findById(any())).thenReturn(null);
        BookController bookController = new BookController(bookService);

        assertThrows(NotFoundException.class,
                ()-> bookController.getById(1));
    }

    @Test
    void whenCreatingBookWithFuturePublicationYear_thenThrowsBadRequest(){
        BookDTO bookDTO = BookDTO.builder().
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.now().plusYears(1)).
                isbn("5341348768").
                build();

        Book book = Book.builder().
                title(bookDTO.getTitle()).
                publicationYear(bookDTO.getPublicationYear()).
                author(bookDTO.getAuthor()).
                isbn(bookDTO.getIsbn()).
                state(State.AVAILABLE).
                build();

        Book savedBook = book;
        savedBook.setId(1);

        when(bookService.save(book)).thenReturn(savedBook);
        BookController bookController = new BookController(bookService);

        assertThrows(BadRequestException.class,
                () -> bookController.create(bookDTO));
    }
}
