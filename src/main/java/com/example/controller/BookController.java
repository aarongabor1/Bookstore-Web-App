package com.example.controller;

import com.example.model.Book;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.UserRepository;
import com.example.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserRepository userRepository;

    public BookController(BookRepository r) {
        bookRepository = r;
    };

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .filter(book -> book.getTitle() != null && book.getAuthor() != null && book.getPrice() != null)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{title}")
    public List<Book> findByTitleContaining(@PathVariable String title) {
        return bookRepository.findByTitleContaining(title);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<?> getBookRecommendations(@PathVariable long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Set<Book> recommendedBooks = recommendationService.recommendBooks(user);
        return ResponseEntity.ok(recommendedBooks);
    }

    private User getCurrentUser() {
        // fetch current user
        return userRepository.findById(1L).orElse(null);

    }

}
