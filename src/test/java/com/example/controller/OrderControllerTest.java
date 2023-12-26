package com.example.controller;

import com.example.model.OrderDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
public class OrderControllerTest {

    private OrderController orderController = Mockito.mock(OrderController.class);

    @Test
    public void orderControllerTest(){


        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCcv("ccv");
        orderDetails.setCity("city");
        orderDetails.setCountry("country");
        orderDetails.setCardNumber("card number");
        orderDetails.setFirstName("first name");
        orderDetails.setExpiryDate("expiry date");
        orderDetails.setLastName("last name");
        orderDetails.setPostalCode("postal code");

        when(orderController.completeOrder(orderDetails)).thenReturn(new ResponseEntity(orderDetails,HttpStatus.ACCEPTED));


        System.out.println(orderController.completeOrder(orderDetails).getBody());
    }
}
