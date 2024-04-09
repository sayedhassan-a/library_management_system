package com.example.library.service;

import com.example.library.entity.Patron;

import java.util.List;

public interface PatronService {
    Patron findById(Integer id);
    List<Patron> findAll();
    Patron save(Patron patron);
    void deleteById(Integer id);
}
