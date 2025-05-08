package com.library.management.service;

import com.library.management.dto.BookDto;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @CircuitBreaker(name = "bookService", fallbackMethod = "getAllBooksFallback")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    public List<BookDto> getAllBooksFallback(Exception e) {
        // Return cached data or empty list in case of failure
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAvailableBooks() {
        return bookRepository.findByAvailableTrue().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        book.setAvailable(true); // New books are available by default
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDto.class);
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.getAvailable());
        book.setPrice(bookDto.getPrice());
        book.setZoner(bookDto.getZoner());
        book.setImagePath(bookDto.getImagePath());
        book.setDetails(bookDto.getDetails());

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> searchBooks(String query) {
        return bookRepository.searchBooks(query.toLowerCase()).stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }
}