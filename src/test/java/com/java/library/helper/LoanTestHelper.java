package com.java.library.helper;

import com.java.library.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.java.library.helper.BookTestHelper.createBook;
import static com.java.library.helper.UserTestHelper.createUser;

public class LoanTestHelper {

    public static Loan createLoan(Long id, User user, Book book, LocalDate loanDate, LocalDate returnDate, LoanStatus status) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);
        loan.setStatus(status);

        return loan;
    }

    public static List<Loan> createMockLoans() {
        Book book = createBook(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);
        Book book2 = createBook(2L, "1984", "George Orwell", "Dystopian", true);
        User user = createUser(3L, "User01", "user01@user.com", "password", Role.USER);
        User user2 = createUser(4L, "User02", "user02@user.com", "password", Role.USER);

        Loan loan01 = createLoan(1L, user, book, LocalDate.now(), LocalDate.now().plusDays(14), LoanStatus.PENDING);
        Loan loan02 = createLoan(2L, user2, book2, LocalDate.now(), LocalDate.now().plusDays(14), LoanStatus.PENDING);

        return Arrays.asList(loan01, loan02);
    }
}
