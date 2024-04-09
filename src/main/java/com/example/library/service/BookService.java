package com.example.library.service;

import com.example.library.entity.Book;

import java.util.List;

public interface BookService {
    Book findById(Integer id);
    List<Book> findAll();
    Book save(Book book);
    void deleteById(Integer id);
}
