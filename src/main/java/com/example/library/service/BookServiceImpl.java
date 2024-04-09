package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.exceptionHandling.exception.NotFoundException;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public Book findById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        if(bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("Book with id:" + id + " does not " +
                    "exist");
        }
    }
}
