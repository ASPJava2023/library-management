package com.library.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    @NotNull
    private Long bookId;

    @NotNull
    private Long userId;

    private LocalDate borrowDate;
    private LocalDate dueDate;
}