package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BorrowingRecord;
import com.example.library.entity.Patron;
import com.example.library.entity.State;
import com.example.library.exceptionHandling.exception.ConflictException;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    @Override
    @Transactional
    public BorrowingRecord returnBook(Integer bookId, Integer patronId) {
        System.out.println(5);
        BorrowingRecord borrowingRecord =
                borrowingRecordRepository.findLastByBookIdAndPatronIdOrderByBorrowDate(bookId, patronId).orElseThrow(() -> new ConflictException("Borrowing not found for book with id:" + bookId + " and patron with id" + patronId));
        if(borrowingRecord.getReturnDate() != null){
            throw new ConflictException("Borrowing not found for book with " +
                    "id:" + bookId + " and patron with id:" + patronId);
        }

        borrowingRecord.setReturnDate(LocalDateTime.now());
        borrowingRecord.getBook().setState(State.AVAILABLE);

        borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecord;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public BorrowingRecord borrowBook(Integer bookId, Integer patronId) {
        Book book = bookService.findById(bookId);
        if(book == null) {
            throw new NotFoundException("No book found for id:" + bookId);
        }
        Patron patron = patronService.findById(patronId);
        if(patron == null) {
            throw new NotFoundException("No patron found for id:" + patronId);
        }
        if(book.getState().equals(State.BORROWED)){
            throw new ConflictException("Book with the id:" + bookId + " is " +
                    "already borrowed");
        }

        book.setState(State.BORROWED);
        BorrowingRecord borrowingRecord = BorrowingRecord.builder().
                book(book).
                patron(patron).
                borrowDate(LocalDateTime.now()).
                build();

        return borrowingRecordRepository.save(borrowingRecord);
    }

}
