package com.example.library.service;

import com.example.library.entity.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordService {
    BorrowingRecord returnBook(Integer bookId, Integer patronId);
    BorrowingRecord borrowBook(Integer bookId, Integer patronId);
}
