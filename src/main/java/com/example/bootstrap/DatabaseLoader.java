package com.example.bootstrap;

import com.example.model.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public DatabaseLoader(BookRepository bookRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, PurchaseRepository purchaseRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.purchaseRepository = purchaseRepository;
    }
    @Override
    public void run(String... args){
        List<Book> books = List.of(new Book(
                "9780393358223", // ISBN
                "Beirut Hellfire Society", // Title
                "/images/bhs.jpg", // Picture
                "Description of the book", // Description
                "Author Name", // Author
                "Publisher Name", // Publisher
                10, // Quantity
                29.99 // Price
        ),
        new Book(
                "9781552453056", // ISBN
                "Fifteen Dogs", // Title
                "/images/15d.jpg", // Picture
                "Description of the book", // Description
                "Alexis, André", // Author
                "Coach House Books", // Publisher
                100, // Quantity
                299.99 // Price
        ),
        new Book(
                "9780809259359", // ISBN
                "Cashews and Lentils, Apples and Oats", // Title
                "/images/img.png", // Picture
                "Description of the book", // Description
                "Dalsass, Diana", // Author
                "AbeBooks", // Publisher
                100, // Quantity
                699.99 // Price
        ),
        new Book(
                "9780023042706", // ISBN
                "The Prince", // Title
                "/images/img_1.png", // Picture
                "Description of the book", // Description
                "Machiavelli, Niccolo", // Author
                "Macmillan Coll Div", // Publisher
                1000, // Quantity
                99.99 // Price
        ));

        books.forEach(bookRepository::save);


        //users
        User user1 = userRepository.save(new User("Kareem", UserRole.REGULAR_USER));
        User user2 = userRepository.save(new User("Alice", UserRole.REGULAR_USER));
        User user3 = userRepository.save(new User("Stefan", UserRole.REGULAR_USER));

        purchaseRepository.save(new Purchase(user1, Arrays.asList(books.get(0), books.get(1))));
        purchaseRepository.save(new Purchase(user2, Arrays.asList(books.get(1), books.get(2))));
        purchaseRepository.save(new Purchase(user3, Arrays.asList(books.get(2), books.get(3))));

        // Link purchases to users and save shopping carts
        List.of(user1, user2, user3).forEach(user -> {
            user.setPurchaseHistory(purchaseRepository.findByUser(user));
            userRepository.save(user);
            shoppingCartRepository.save(new ShoppingCart(user));
        });
    }
}
