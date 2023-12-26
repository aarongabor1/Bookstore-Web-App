package com.example.controller;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
    private BookController bookController = Mockito.mock(BookController.class);

    @Test
    public void bookControllerTest(){

        //bookController = new BookController(bookRepository);



        Book book1 = new Book("123456789", "Sample Book", "url/to/picture", "Description",
                "Author Name", "Publisher", 10, 29.99);
        Book book2 = new Book("1234567892", "Sample Book2", "url/to/picture2", "Description2",
                "Author Name2", "Publisher2", 102, 29.992);
        book1.setId(0L);


        ArrayList<Book> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);

        HttpStatus b = HttpStatus.ACCEPTED;

        when(bookController.findByTitleContaining("Sample")).thenReturn(list);
        when(bookController.getAllBooks()).thenReturn(list);
        when(bookController.getBookById(book1.getId())).thenReturn(new ResponseEntity(book1,HttpStatus.ACCEPTED));


        bookController.addBook(book1);
        bookController.addBook(book2);


        /**
         * assertions for the book controller's methods
         */
        assertEquals(list,bookController.findByTitleContaining("Sample"));
        assertEquals(list,bookController.getAllBooks());
        assertEquals(book1,bookController.getBookById(book1.getId()).getBody());






    }
}
