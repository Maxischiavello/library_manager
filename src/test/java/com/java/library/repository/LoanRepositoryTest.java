package com.java.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.java.library.model.Loan;
import com.java.library.model.User;
import com.java.library.model.Role;
import com.java.library.repository.LoanRepository;
import com.java.library.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserId() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setName("Test User");
        user.setRole(Role.USER);

        userRepository.save(user);

        Loan loan = new Loan();
        loan.setUser(user);
        loanRepository.save(loan);

        List<Loan> loans = loanRepository.findByUserId(user.getId());
        assertThat(loans).isNotEmpty();
        assertThat(loans.get(0).getUser().getUsername()).isEqualTo("username");
    }
}
