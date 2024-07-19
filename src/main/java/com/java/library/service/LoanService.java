package com.java.library.service;

import com.java.library.model.Loan;
import com.java.library.model.LoanStatus;
import com.java.library.model.User;
import com.java.library.repository.LoanRepository;
import com.java.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

//    public LoanDTO requestLoan(LoanDTO loanDTO) {
//        Loan loan = convertToEntity(loanDTO);
//        loan.setLoanDate(LocalDate.now());
//        loan.setStatus(LoanStatus.PENDING);
//        return convertToDTO(loanRepository.save(loan));
//    }
//
//    public List<LoanDTO> getAllLoans() {
//        return loanRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public Optional<LoanDTO> getLoanById(Long id) {
//        return loanRepository.findById(id).map(this::convertToDTO);
//    }
//
//    public LoanDTO approveLoan(Long id) {
//        Optional<Loan> loanOptional = loanRepository.findById(id);
//        if (loanOptional.isPresent()) {
//            Loan loan = loanOptional.get();
//            loan.setStatus(LoanStatus.APPROVED);
//            return convertToDTO(loanRepository.save(loan));
//        }
//        return null;
//    }
//
//    public LoanDTO rejectLoan(Long id) {
//        Optional<Loan> loanOptional = loanRepository.findById(id);
//        if (loanOptional.isPresent()) {
//            Loan loan = loanOptional.get();
//            loan.setStatus(LoanStatus.REJECTED);
//            return convertToDTO(loanRepository.save(loan));
//        }
//        return null;
//    }
//
//    private Loan convertToEntity(LoanDTO loanDTO) {
//        Loan loan = new Loan();
//        loan.setId(loanDTO.getId());
//        Optional<User> user = userRepository.findById(loanDTO.getUserId());
//        user.ifPresent(loan::setUser);
//        loan.setLoanDate(loanDTO.getLoanDate());
//        loan.setReturnDate(loanDTO.getReturnDate());
//        loan.setStatus(LoanStatus.valueOf(loanDTO.getStatus()));
//        return loan;
//    }
//
//    private LoanDTO convertToDTO(Loan loan) {
//        LoanDTO loanDTO = new LoanDTO();
//        loanDTO.setId(loan.getId());
//        loanDTO.setUserId(loan.getUser().getId());
//        loanDTO.setBookId(loan.getBook().getId());
//        loanDTO.setLoanDate(loan.getLoanDate());
//        loanDTO.setReturnDate(loan.getReturnDate());
//        loanDTO.setStatus(loan.getStatus().name());
//        return loanDTO;
//    }
}
