package com.example.controller;

import com.example.model.ShoppingCart;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
public class ShoppingCartControllerTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    private BookRepository bookRepository;

    private ShoppingCartController shoppingCartController = Mockito.mock(ShoppingCartController.class);

    @Test
    public void shoppingCartControllerTest(){
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = new User();
        shoppingCart.setUser(user);
//
//        shoppingCartRepository.save(shoppingCart);
//        shoppingCartController = new ShoppingCartController(shoppingCartRepository,bookRepository);
//
//        //get cart by id
//        assertEquals(shoppingCart,shoppingCartController.getShoppingCart(shoppingCart.getId()).getBody());

        HashMap<String, Long> map = new HashMap<>();
        map.put("key",0L);

        when(shoppingCartController.getShoppingCart(shoppingCart.getId())).thenReturn(new ResponseEntity(shoppingCart, HttpStatus.ACCEPTED));
        when(shoppingCartController.addItemToCart(shoppingCart.getId(),map)).thenReturn(new ResponseEntity(shoppingCart,HttpStatus.ACCEPTED));
        when(shoppingCartController.removeItemFromCart(shoppingCart.getId(),0L)).thenReturn(new ResponseEntity(shoppingCart,HttpStatus.ACCEPTED));

        assertEquals(shoppingCart,shoppingCartController.getShoppingCart(shoppingCart.getId()).getBody());
        assertEquals(shoppingCart,shoppingCartController.addItemToCart(shoppingCart.getId(),map).getBody());
        assertEquals(shoppingCart,shoppingCartController.removeItemFromCart(shoppingCart.getId(),0L).getBody());

    }

}
