package com.library.management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String author;

    @NotBlank
    @Size(max = 50)
    private String category;

    @NotBlank
    @Size(max = 20)
    private String isbn;

    @NotNull
    private Boolean available;

    @NotNull
    @Positive
    private Double price;

    @Size(max = 50)
    private String zoner;

    private String imagePath;

    private String details;
}