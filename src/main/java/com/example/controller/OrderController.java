package com.example.controller;

import com.example.model.OrderDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping("/complete")
    public ResponseEntity<?> completeOrder(@RequestBody OrderDetails orderDetails) {
        try {
            // Your order processing logic
            String orderConfirmation = "Order processed successfully with ID: " + generateOrderId();

            // Make sure to return a proper JSON object
            return ResponseEntity.ok().body(Collections.singletonMap("message", orderConfirmation));
        } catch (Exception e) {
            // Respond with a JSON error message
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    private String processOrder(OrderDetails orderDetails) {
        // Implement the business logic here
        // This is just a placeholder, in a real application, you would have detailed logic
        return "Order processed successfully with ID: " + generateOrderId();
    }

    private String generateOrderId() {
        // Generate a unique order ID
        return "ORD" + System.currentTimeMillis();
    }
}
