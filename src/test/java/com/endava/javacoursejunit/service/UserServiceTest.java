package com.endava.javacoursejunit.service;

import com.endava.javacoursejunit.domain.User;
import com.endava.javacoursejunit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User mockUser = new User("mockuser");

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockUser));

        User actual = userService.getUserById(1);

        assertEquals(mockUser.getUsername(), actual.getUsername(), "Username should be mockuser");

    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenNoUserFound() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1));

    }

    @Test
    void shouldSaveNewUserWithUsername() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        userService.saveUserWithUsername(mockUser.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUserById() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockUser));

        userService.updateUser(1, "mockuser2");

        verify(userRepository).save(userCaptor.capture());
        assertEquals("mockuser2", userCaptor.getValue().getUsername(), "Username should be changed");

    }
}
