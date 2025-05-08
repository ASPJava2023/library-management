package com.library.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendBorrowConfirmation(String toEmail, String bookTitle, LocalDate dueDate) {
        // In a real application, this would send an actual email
        logger.info("Sending borrow confirmation to: {}", toEmail);
        logger.info("Book: {}, Due Date: {}", bookTitle, dueDate);
        // Simulate email sending delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Async
    public void sendOverdueNotification(String toEmail, String bookTitle, LocalDate dueDate) {
        // In a real application, this would send an actual email
        logger.info("Sending overdue notification to: {}", toEmail);
        logger.info("Book: {}, Due Date: {}", bookTitle, dueDate);
        // Simulate email sending delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}