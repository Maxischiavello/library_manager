package com.java.library.service;

import com.java.library.exception.UserNotFoundException;
import com.java.library.helper.BookTestHelper;
import com.java.library.helper.UserTestHelper;
import com.java.library.model.Book;
import com.java.library.model.Role;
import com.java.library.model.User;
import com.java.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = UserTestHelper.createMockUsers();

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("User01", result.get(0).getName());
        assertEquals("user02@user.com", result.get(1).getUsername());
        assertEquals(Role.ADMIN, result.get(4).getRole());
    }

    @Test
    void testGetUser() {
        List<User> mockUsers = UserTestHelper.createMockUsers();
        User user = mockUsers.get(0);

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        User result = userService.getUser(3L).orElse(null);
        assertNotNull(result);
        assertEquals("User01", result.getName());
        assertEquals("user01@user.com", result.getUsername());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = UserTestHelper.createUser(userId, "ExistingUser", "existing@user.com", "password", Role.USER);
        User updatedUser = UserTestHelper.createUser(userId, "UpdatedUser", "updated@user.com", "password", Role.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        when(passwordEncoder.encode("password")).thenReturn("password");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertNotNull(result);
        assertEquals("UpdatedUser", capturedUser.getName());
        assertEquals("updated@user.com", capturedUser.getUsername());
        assertEquals(Role.ADMIN, capturedUser.getRole());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = UserTestHelper.createUser(userId, "UpdatedUser", "updated@user.com", "newPassword", Role.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        verify(userRepository).findById(userId);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User existingUser = UserTestHelper.createUser(userId, "ExistingUser", "existing@user.com", "password", Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(existingUser);
    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });

        verify(userRepository).findById(userId);
    }
}
