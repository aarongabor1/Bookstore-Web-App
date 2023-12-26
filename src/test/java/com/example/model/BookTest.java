package com.example.model;
import com.example.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateReadDeleteBook() {
        Book book = new Book("123456789", "Sample Book", "url/to/picture", "Description", "Author Name", "Publisher", 10, 29.99);

        // Create
        book = bookRepository.save(book);
        assertNotNull(book.getId());

        // Read
        Book foundBook = bookRepository.findById(book.getId()).orElse(null);
        assertNotNull(foundBook);
        assertEquals("Sample Book", foundBook.getTitle());

        // Delete
        bookRepository.delete(book);
        assertFalse(bookRepository.findById(book.getId()).isPresent());
    }
}
