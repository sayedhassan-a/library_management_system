package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BorrowingRecord;
import com.example.library.entity.Patron;
import com.example.library.entity.State;
import com.example.library.exceptionHandling.exception.ConflictException;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BorrowingRecordServiceUnitTest {
    @MockBean
    private BorrowingRecordRepository borrowingRecordRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private PatronService patronService;

    @Test
    void givenNoBooks_whenBorrow_thenThrowNotFoundException() {
        when(bookService.findById(any())).thenReturn(null);
        Patron patron = Patron.builder().
                id(1).
                firstName("John").
                lastName("Doe").
                email("johndoe@example.com").
                address("10 first street").
                build();
        when(patronService.findById(any())).thenReturn(patron);
        BorrowingRecordService borrowingRecordService =
                new BorrowingRecordServiceImpl(borrowingRecordRepository,
                        bookService,patronService);

        assertThrows(NotFoundException.class,
                () -> borrowingRecordService.borrowBook(1,1),"No book found " +
                        "for id 1");
    }

    @Test
    void givenNoPatrons_whenBorrow_thenThrowNotFoundException() {
        when(patronService.findById(any())).thenReturn(null);
        Book book = Book.builder().
                id(1).
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.of(2013)).
                state(State.AVAILABLE).
                isbn("5341348768").
                build();
        when(bookService.findById(any())).thenReturn(book);
        BorrowingRecordService borrowingRecordService =
                new BorrowingRecordServiceImpl(borrowingRecordRepository,
                        bookService,patronService);

        assertThrows(NotFoundException.class,
                () -> borrowingRecordService.borrowBook(1,1),"No patron found" +
                        " for id:1");
    }

    @Test
    void givenBorrowedBook_whenBorrow_thenThrowConflictException(){
        Book book = Book.builder().
                id(1).
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.of(2013)).
                state(State.BORROWED).
                isbn("5341348768").
                build();
        Patron patron = Patron.builder().
                id(1).
                firstName("John").
                lastName("Doe").
                email("johndoe@example.com").
                address("10 first street").
                build();
        when(bookService.findById(1)).thenReturn(book);
        when(patronService.findById(1)).thenReturn(patron);
        BorrowingRecordService borrowingRecordService =
                new BorrowingRecordServiceImpl(borrowingRecordRepository,
                        bookService,patronService);

        assertThrows(ConflictException.class,
                () -> borrowingRecordService.borrowBook(1,1),
                "Book with the id:1 is already borrowed");

    }

    @Test
    void givenAvailableBookAndPatron_whenBorrow_thenReturnBorrowRecordAndSetBookToBorrowed(){
        Book book = Book.builder().
                id(1).
                title("Master Spring Boot").
                author("Jack Bauer").
                publicationYear(Year.of(2013)).
                state(State.AVAILABLE).
                isbn("5341348768").
                build();
        Patron patron = Patron.builder().
                id(1).
                firstName("John").
                lastName("Doe").
                email("johndoe@example.com").
                address("10 first street").
                build();
        when(bookService.findById(1)).thenReturn(book);
        when(patronService.findById(1)).thenReturn(patron);
        when(borrowingRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        BorrowingRecordService borrowingRecordService =
                new BorrowingRecordServiceImpl(borrowingRecordRepository,
                        bookService,patronService);

        BorrowingRecord result = borrowingRecordService.borrowBook(1,1);

        assertEquals(result.getBook().getState(),State.BORROWED);

    }
}
