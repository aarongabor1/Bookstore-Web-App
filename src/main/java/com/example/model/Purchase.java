package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "purchase_books",
            joinColumns = @JoinColumn(name="purchase_id"),
            inverseJoinColumns = @JoinColumn(name="book_id")
    )
    private List<Book> books;
    private Double totalCost;

    public Purchase(){

    }
    public Purchase(User user, List<Book> books) {
        this.user = user;
        this.books = books;

    }

    public Long getId() {
        return id;
    }
}
