package com.example.library.controller;

import com.example.library.entity.BorrowingRecord;
import com.example.library.service.BorrowingRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Integer bookId,
                                                 @PathVariable Integer patronId) {
        return ResponseEntity.ok(borrowingRecordService.borrowBook(bookId,
                patronId));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Integer bookId,
                                                      @PathVariable Integer patronId){
        return ResponseEntity.ok(borrowingRecordService.returnBook(bookId,
                patronId));

    }

}
