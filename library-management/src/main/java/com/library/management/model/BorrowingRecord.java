package com.library.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
@Getter
@Setter
@NoArgsConstructor
public class BorrowingRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    public BorrowingRecord(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = BorrowingStatus.BORROWED;
    }
}

enum BorrowingStatus {
    BORROWED,
    RETURNED,
    OVERDUE
}