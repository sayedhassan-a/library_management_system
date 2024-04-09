package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.State;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookServiceUnitTest {
    @MockBean
    private BookRepository bookRepository;

    @Test
    void givenNoBooks_whenGetAllBooks_thenReturnEmptyList() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        List<?> booksList = bookService.findAll();

        assertTrue(booksList.isEmpty());
    }

    @Test
    void givenOneBook_whenFindById_thenReturnOneBook() {
        Book expectedBook = Book.builder().
                id(1).
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.of(2013)).
                state(State.BORROWED).
                isbn("5341348768").
                build();
        when(bookRepository.findById(1)).thenReturn(Optional.of(expectedBook));
        BookService bookService = new BookServiceImpl(bookRepository);

        Book result = bookService.findById(1);

        assertEquals(expectedBook, result);
    }

    @Test
    void givenNoBook_whenFindById_thenReturnNull() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        BookService bookService = new BookServiceImpl(bookRepository);

        Book result = bookService.findById(1);

        assertNull(result);
    }

    @Test
    void givenNoBooks_whenDeleteById_thenThrowNotFoundException() {
        when(bookRepository.existsById(1)).thenReturn(false);
        BookService bookService = new BookServiceImpl(bookRepository);

        assertThrows(NotFoundException.class, () -> bookService.deleteById(1));
    }
}
