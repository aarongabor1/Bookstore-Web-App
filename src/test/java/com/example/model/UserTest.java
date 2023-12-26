package com.example.model;

import com.example.repository.BookRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateReadDeleteBook() {
        User user = new User();

        // Create
        user = userRepository.save(user);
        assertNotNull(user.getId());

        // Read
        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(foundUser);

        // Delete
        userRepository.delete(user);
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
