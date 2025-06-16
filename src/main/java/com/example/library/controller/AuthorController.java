package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAll() {
        return authorService.findAll();
    }

    @PostMapping
    public Author create(@Valid @RequestBody Author author) {
        return authorService.save(author);
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Valid @RequestBody Author author) {
        return authorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
