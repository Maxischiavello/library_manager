package com.java.library.service;

import com.java.library.exception.BookNotFoundException;
import com.java.library.exception.LoanNotFoundException;
import com.java.library.exception.UserNotFoundException;
import com.java.library.model.Book;
import com.java.library.model.Loan;
import com.java.library.model.LoanStatus;
import com.java.library.model.User;
import com.java.library.repository.BookRepository;
import com.java.library.repository.LoanRepository;
import com.java.library.repository.UserRepository;
import com.java.library.util.Messages;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private static final int LOAN_DAYS = 14;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    private final Messages messages = new Messages();

    public Loan requestLoan(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(messages.USER_NOT_FOUND() + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(messages.BOOK_NOT_FOUND() + bookId));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(LOAN_DAYS));
        loan.setStatus(LoanStatus.PENDING);

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoan(Long id) {
        return loanRepository.findById(id);
    }

    public Loan approveLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(messages.LOAN_NOT_FOUND() + id));

        loan.setStatus(LoanStatus.APPROVED);
        return loanRepository.save(loan);
    }

    public Loan rejectLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(messages.LOAN_NOT_FOUND() + id));

        loan.setStatus(LoanStatus.REJECTED);
        return loanRepository.save(loan);
    }
}
