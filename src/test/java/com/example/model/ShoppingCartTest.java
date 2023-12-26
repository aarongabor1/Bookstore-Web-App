package com.example.model;

import com.example.repository.BookRepository;
import com.example.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ShoppingCartTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    public void testCreateReadDeleteBook() {
        ShoppingCart shoppingCart = new ShoppingCart();

        // Create
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        assertNotNull(shoppingCart.getId());

        // Read
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).orElse(null);
        assertNotNull(foundShoppingCart);

        // Delete
        shoppingCartRepository.delete(shoppingCart);
        assertFalse(shoppingCartRepository.findById(shoppingCart.getId()).isPresent());
    }
}
