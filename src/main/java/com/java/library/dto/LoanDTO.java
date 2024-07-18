package com.java.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
