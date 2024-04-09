package com.example.library.repository;

import com.example.library.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Integer> {
    Optional<BorrowingRecord> findLastByBookIdAndPatronIdOrderByBorrowDate(Integer bookId, Integer patronId);
}
