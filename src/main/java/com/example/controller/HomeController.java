package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "cart/checkout";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "cart/confirmation";
    }
}
