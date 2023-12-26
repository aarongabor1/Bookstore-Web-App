package com.example.service;

import com.example.model.Book;
import com.example.model.User;
import com.example.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void recommendBooksTest() {
        // Arrange
        User currentUser = new User(); // Mock current user
        currentUser.setId(1L);

        User similarUser = new User(); // Mock similar user
        similarUser.setId(2L);

        // Mock books
        Book book1 = new Book(
                "9780393358223", // ISBN
                "Beirut Hellfire Society", // Title
                "/images/bhs.jpg", // Picture
                "Description of the book", // Description
                "Author Name", // Author
                "Publisher Name", // Publisher
                10, // Quantity
                29.99 // Price
        );
        Book book2 = new Book(
                "9781552453056", // ISBN
                "Fifteen Dogs", // Title
                "/images/15d.jpg", // Picture
                "Description of the book", // Description
                "Alexis, André", // Author
                "Coach House Books", // Publisher
                100, // Quantity
                299.99 // Price
        );

        // Setup mock repository responses
        when(purchaseRepository.findAllUsersExcept(currentUser)).thenReturn(Arrays.asList(similarUser));
        when(purchaseRepository.findBooksByUser(similarUser)).thenReturn(new HashSet<>(Arrays.asList(book1, book2)));
        when(purchaseRepository.findBooksByUser(currentUser)).thenReturn(new HashSet<>(Arrays.asList(book1)));

        // Act
        Set<Book> recommendedBooks = recommendationService.recommendBooks(currentUser);

        // Assert
        assertTrue(recommendedBooks.contains(book2), "Recommended books should contain book2");
        assertEquals(1, recommendedBooks.size(), "There should be only one recommended book");
    }
}