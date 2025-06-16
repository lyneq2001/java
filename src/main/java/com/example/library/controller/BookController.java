package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @PostMapping
    public Book create(@Valid @RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/author/{authorId}")
    public List<Book> getByAuthor(@PathVariable Long authorId) {
        return bookService.findByAuthor(authorId);
    }
}
