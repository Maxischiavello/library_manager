package com.java.library.controller;

import com.java.library.exception.*;
import com.java.library.model.Book;
import com.java.library.model.Loan;
import com.java.library.model.User;
import com.java.library.service.BookService;
import com.java.library.service.LoanService;
import com.java.library.service.UserService;
import com.java.library.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoanService loanService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id).orElseThrow(() -> new BookNotFoundException(Messages.USER_NOT_FOUND() + id)));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserUpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBook(id).orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND() + id)));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (BookUpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanService.getLoan(id).orElseThrow(() -> new LoanNotFoundException(Messages.LOAN_NOT_FOUND() + id)));
        } catch (LoanNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/loans/approve/{id}")
    public ResponseEntity<Loan> approveLoan(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanService.approveLoan(id));
        } catch (LoanNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/loans/reject/{id}")
    public ResponseEntity<Loan> rejectLoan(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanService.rejectLoan(id));
        } catch (LoanNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
