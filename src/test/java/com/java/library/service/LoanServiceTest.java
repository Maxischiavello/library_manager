package com.java.library.service;

import com.java.library.helper.BookTestHelper;
import com.java.library.helper.LoanTestHelper;
import com.java.library.helper.UserTestHelper;
import com.java.library.model.*;
import com.java.library.repository.BookRepository;
import com.java.library.repository.LoanRepository;
import com.java.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private LoanService loanService;

    @Test
    void testGetAllBooks() {
        List<Loan> mockLoans = LoanTestHelper.createMockLoans();

        when(loanRepository.findAll()).thenReturn(mockLoans);

        List<Loan> result = loanService.getAllLoans();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testRequestLoan() {
        User user = UserTestHelper.createUser(3L, "User01", "user01@user.com", "password", Role.USER);
        Book book = BookTestHelper.createBook(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.requestLoan(3L, 1L);

        assertNotNull(result);

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan capturedLoan = loanCaptor.getValue();
        assertNotNull(capturedLoan);
        assertEquals(user, capturedLoan.getUser());
        assertEquals(book, capturedLoan.getBook());
    }

    @Test
    void testApproveLoan() {
        Long loanId = 1L;
        User user = UserTestHelper.createUser(3L, "User01", "user01@user.com", "password", Role.USER);
        Book book = BookTestHelper.createBook(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);
        Loan loan = LoanTestHelper.createLoan(loanId, user, book, LocalDate.now(), LocalDate.now().plusDays(14), LoanStatus.PENDING);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.approveLoan(loanId);
        assertNotNull(result);
        assertEquals(LoanStatus.APPROVED, result.getStatus());

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan capturedLoan = loanCaptor.getValue();
        assertNotNull(capturedLoan);
        assertEquals(LoanStatus.APPROVED, capturedLoan.getStatus());
    }

    @Test
    void testRejectLoan() {
        Long loanId = 1L;
        User user = UserTestHelper.createUser(3L, "User01", "user01@user.com", "password", Role.USER);
        Book book = BookTestHelper.createBook(1L, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", true);
        Loan loan = LoanTestHelper.createLoan(loanId, user, book, LocalDate.now(), LocalDate.now().plusDays(14), LoanStatus.PENDING);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.rejectLoan(loanId);
        assertNotNull(result);
        assertEquals(LoanStatus.REJECTED, result.getStatus());

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan capturedLoan = loanCaptor.getValue();
        assertNotNull(capturedLoan);
        assertEquals(LoanStatus.REJECTED, capturedLoan.getStatus());
    }
}
