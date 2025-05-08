package com.library.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {

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
    private Boolean available = true;

    @NotNull
    private Double price;

    @Size(max = 50)
    private String zoner;

    private String imagePath;

    @Column(columnDefinition = "TEXT")
    private String details;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowingRecord> borrowingRecords = new HashSet<>();

    public Book(String title, String author, String category, String isbn, Double price) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.price = price;
    }
}