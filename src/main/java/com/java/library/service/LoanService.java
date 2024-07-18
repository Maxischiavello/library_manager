package com.java.library.service;

import com.java.library.dto.LoanDTO;
import com.java.library.model.Loan;
import com.java.library.model.User;
import com.java.library.model.Book;
import com.java.library.repository.LoanRepository;
import com.java.library.repository.UserRepository;
import com.java.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<LoanDTO> getLoanById(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.map(this::convertToDTO);
    }

    public LoanDTO saveLoan(LoanDTO loanDTO) {
        Loan loan = convertToEntity(loanDTO);
        Loan savedLoan = loanRepository.save(loan);
        return convertToDTO(savedLoan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setUserId(loan.getUser().getId());
        loanDTO.setBookId(loan.getBook().getId());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setReturnDate(loan.getReturnDate());
        return loanDTO;
    }

    private Loan convertToEntity(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setId(loanDTO.getId());

        Optional<User> user = userRepository.findById(loanDTO.getUserId());
        user.ifPresent(loan::setUser);

        Optional<Book> book = bookRepository.findById(loanDTO.getBookId());
        book.ifPresent(loan::setBook);

        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setReturnDate(loanDTO.getReturnDate());

        return loan;
    }
}
