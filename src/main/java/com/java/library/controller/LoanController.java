package com.java.library.controller;

import com.java.library.model.Loan;
import com.java.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/request")
    public ResponseEntity<Loan> requestLoan(@RequestBody Long userId, Long bookId) {
        Loan loan = loanService.requestLoan(userId, bookId);
        return ResponseEntity.ok(loan);
    }
}