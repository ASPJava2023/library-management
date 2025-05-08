package com.library.management.controller;

import com.library.management.dto.BorrowRequest;
import com.library.management.dto.BorrowingRecordDto;
import com.library.management.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Borrowings", description = "Book borrowing management API")
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Borrow a book", description = "Borrow a book (Librarian only)")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@Valid @RequestBody BorrowRequest borrowRequest) {
        return new ResponseEntity<>(borrowingService.borrowBook(borrowRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Return a book", description = "Return a borrowed book (Librarian only)")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.returnBook(id));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN') or #userId == principal.id")
    @Operation(summary = "Get user borrowing history", description = "Get borrowing history for a user")
    public ResponseEntity<List<BorrowingRecordDto>> getUserBorrowingHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowingService.getUserBorrowingHistory(userId));
    }

    @GetMapping("/users/{userId}/current")
    @PreAuthorize("hasRole('LIBRARIAN') or #userId == principal.id")
    @Operation(summary = "Get current borrowings", description = "Get currently borrowed books for a user")
    public ResponseEntity<List<BorrowingRecordDto>> getCurrentBorrowings(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowingService.getCurrentBorrowings(userId));
    }
}