package com.library.management.dto;

import com.library.management.model.BorrowingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecordDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private BorrowingStatus status;
}