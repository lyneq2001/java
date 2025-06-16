package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, Author updated) {
        Author author = findById(id);
        author.setName(updated.getName());
        return authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
