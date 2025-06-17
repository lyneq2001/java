package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
    List<Author> findByNameContainingIgnoreCase(String part);

    @Query("SELECT a FROM Author a WHERE LENGTH(a.name) > :length")
    List<Author> findWithNameLongerThan(int length);
}
