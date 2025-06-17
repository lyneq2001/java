package com.example.library.service;

import com.example.library.model.AppUser;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.repository.AppUserRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final AppUserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository,
                       AppUserRepository userRepository,
                       BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> findAll() { return loanRepository.findAll(); }

    public List<Loan> findByUser(Long userId) { return loanRepository.findByUserId(userId); }

    @Transactional
    public Loan borrowBook(Long userId, Long bookId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        Loan loan = new Loan(book, user);
        return loanRepository.save(loan);
    }

    @Transactional
    public void returnLoan(Long loanId) { loanRepository.deleteById(loanId); }

    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
    }
}
