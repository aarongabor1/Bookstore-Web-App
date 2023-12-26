package com.example.controller;

import com.example.model.Book;
import com.example.model.ShoppingCart;
import com.example.repository.BookRepository;
import com.example.repository.ShoppingCartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository, BookRepository bookRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long id){
        return shoppingCartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/addItem")
    public ResponseEntity<?> addItemToCart(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        Long bookId = body.get("bookId");
        Optional<ShoppingCart> cartO = shoppingCartRepository.findById(id);
        if(cartO.isEmpty()){
            return ResponseEntity.badRequest().body("Shopping Cart not found for user id " + id );
        }

        ShoppingCart cart = cartO.get();
        Book bookToAdd = bookRepository.findById(bookId).orElse(null);
        if(bookToAdd == null){
            return ResponseEntity.badRequest().body("Book not found for ID " + bookId);
        }

        // Check if the book is already in the cart
        Optional<Book> existingBook = cart.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();

        if(existingBook.isPresent()){
            // Increment quantity if book is already in the cart
            existingBook.get().setQuantity(existingBook.get().getQuantity() + 1);
        } else {
            // Add new book with quantity 1
            bookToAdd.setQuantity(1);
            cart.getBooks().add(bookToAdd);
        }

        shoppingCartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}/removeItem")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long id, @RequestBody Long bookId){
        Optional<ShoppingCart> cartO = shoppingCartRepository.findById(id);
        if(cartO.isEmpty()){
            return ResponseEntity.badRequest().body("Shopping cart not found for id " + id);
        }

        ShoppingCart cart =  cartO.get();
        cart.getBooks().removeIf(book -> book.getId().equals(bookId));
        shoppingCartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ShoppingCart> getShoppingCartByUserId(@PathVariable Long userId){
        return  shoppingCartRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}