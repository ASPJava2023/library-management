package com.library.management.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(Long bookId) {
        super(String.format("Book with ID %d is not available for borrowing", bookId));
    }
}