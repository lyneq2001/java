package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.PublisherRepository;
import com.example.library.repository.GenreRepository;
import com.example.library.model.Author;
import com.example.library.model.Publisher;
import com.example.library.model.Genre;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updated) {
        Book book = findById(id);
        book.setTitle(updated.getTitle());
        if (updated.getAuthor() != null) {
            Long authorId = updated.getAuthor().getId();
            if (authorId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author id must be provided");
            }
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
            book.setAuthor(author);
        }
        if (updated.getPublisher() != null) {
            Long pubId = updated.getPublisher().getId();
            Publisher p = publisherRepository.findById(pubId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
            book.setPublisher(p);
        }
        if (updated.getGenres() != null) {
            Set<Genre> genres = new java.util.HashSet<>();
            for (Genre g : updated.getGenres()) {
                if (g.getId() != null) {
                    genres.add(genreRepository.findById(g.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found")));
                }
            }
            book.setGenres(genres);
        }
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<Book> findByGenreSorted(Long genreId) {
        return bookRepository.findByGenreSorted(genreId);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
