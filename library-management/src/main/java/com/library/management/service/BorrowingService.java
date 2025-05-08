package com.library.management.service;

import com.library.management.dto.BorrowRequest;
import com.library.management.dto.BorrowingRecordDto;
import com.library.management.exception.BookNotAvailableException;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.model.*;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowingRecordRepository;
import com.library.management.repository.UserRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    private static final int DEFAULT_BORROWING_DAYS = 14;

    @Transactional
    @Retry(name = "borrowingService", fallbackMethod = "borrowBookFallback")
    public BorrowingRecordDto borrowBook(BorrowRequest borrowRequest) {
        Book book = bookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", borrowRequest.getBookId()));

        if (!book.getAvailable()) {
            throw new BookNotAvailableException(book.getId());
        }

        User user = userRepository.findById(borrowRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", borrowRequest.getUserId()));

        LocalDate borrowDate = borrowRequest.getBorrowDate() != null ?
                borrowRequest.getBorrowDate() : LocalDate.now();
        LocalDate dueDate = borrowRequest.getDueDate() != null ?
                borrowRequest.getDueDate() : borrowDate.plusDays(DEFAULT_BORROWING_DAYS);

        BorrowingRecord record = new BorrowingRecord(book, user, borrowDate, dueDate);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(record);

        // Update book availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Send notification email
        emailService.sendBorrowConfirmation(user.getEmail(), book.getTitle(), dueDate);

        return modelMapper.map(savedRecord, BorrowingRecordDto.class);
    }

    public BorrowingRecordDto borrowBookFallback(BorrowRequest borrowRequest, Exception e) {
        // Log the error and return null or throw a different exception
        throw new RuntimeException("Borrowing service is temporarily unavailable. Please try again later.");
    }

    @Transactional
    public BorrowingRecordDto returnBook(Long recordId) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord", "id", recordId));

        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowingStatus.RETURNED);

        // Update book availability
        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return modelMapper.map(updatedRecord, BorrowingRecordDto.class);
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDto> getUserBorrowingHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return borrowingRecordRepository.findByUser(user).stream()
                .map(record -> modelMapper.map(record, BorrowingRecordDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BorrowingRecordDto> getCurrentBorrowings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return borrowingRecordRepository.findByUserAndStatus(user, BorrowingStatus.BORROWED).stream()
                .map(record -> modelMapper.map(record, BorrowingRecordDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void checkOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<BorrowingRecord> overdueRecords = borrowingRecordRepository
                .findByDueDateBeforeAndStatus(today, BorrowingStatus.BORROWED);

        overdueRecords.forEach(record -> {
            record.setStatus(BorrowingStatus.OVERDUE);
            borrowingRecordRepository.save(record);

            // Send overdue notification
            emailService.sendOverdueNotification(
                    record.getUser().getEmail(),
                    record.getBook().getTitle(),
                    record.getDueDate()
            );
        });
    }
}