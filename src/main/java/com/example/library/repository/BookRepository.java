package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPublisherId(Long publisherId);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.id = :genreId ORDER BY b.title ASC")
    List<Book> findByGenreSorted(Long genreId);
}
