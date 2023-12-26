package com.example.model;


import com.example.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PurchaseTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void createReadDeletePurchase(){

        Purchase purchase = new Purchase();

        //create
        purchase = purchaseRepository.save(purchase);
        assertNotNull(purchase);

        //read
        Purchase foundPurchase = purchaseRepository.findById(purchase.getId()).orElse(null);
        assertNotNull(foundPurchase);

        //delete
        purchaseRepository.delete(purchase);
        assertFalse(purchaseRepository.findById(purchase.getId()).isPresent());
    }
}
