package com.java.library.controller;

import com.java.library.model.Loan;
import com.java.library.model.LoanRequest;
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
    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequest loanRequest) {
        Loan loan = loanService.requestLoan(loanRequest.getUserId(), loanRequest.getBookId());
        return ResponseEntity.ok(loan);
    }
}