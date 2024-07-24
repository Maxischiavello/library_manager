package com.java.library.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.java.library.jwt.JwtService;
import com.java.library.model.*;
import com.java.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void testLogin() {
        LoginRequest request = new LoginRequest("user01", "password");
        User user = User.builder().username("user01").password("encodedPassword").role(Role.USER).build();

        when(userRepository.findByUsername("user01")).thenReturn(Optional.of(user));
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("mockToken");

        AuthResponse response = authService.login(request);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authTokenCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(authTokenCaptor.capture());

        UsernamePasswordAuthenticationToken capturedAuthToken = authTokenCaptor.getValue();

        assertEquals("user01", capturedAuthToken.getPrincipal());
        assertEquals("password", capturedAuthToken.getCredentials());
        assertEquals("mockToken", response.getToken());
    }

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest("User01", "user01", "password");
        User user = User.builder()
                .username("user01")
                .password("encodedPassword")
                .name("User01")
                .role(Role.USER)
                .build();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("mockToken");

        AuthResponse response = authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals("user01", capturedUser.getUsername());
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals("User01", capturedUser.getName());
        assertEquals(Role.USER, capturedUser.getRole());
        assertEquals("mockToken", response.getToken());
    }
}
