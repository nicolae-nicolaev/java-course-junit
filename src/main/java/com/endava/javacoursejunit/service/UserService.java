package com.endava.javacoursejunit.service;

import com.endava.javacoursejunit.domain.User;
import com.endava.javacoursejunit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private static final String NO_USER_ERROR_MESSAGE = "No users with specified id found!";

    private UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_USER_ERROR_MESSAGE));
    }

    public void saveUserWithUsername(String username) {
        User newUser = new User(username);
        saveUser(newUser);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(NO_USER_ERROR_MESSAGE));

        user.setUsername(username);

        saveUser(user);
    }
}
