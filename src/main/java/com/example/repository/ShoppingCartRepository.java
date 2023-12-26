package com.example.repository;

import com.example.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(Long userId);
    //Optional<ShoppingCart> findById(long Id);
    //Boolean existsByUserId(long id);
    //Boolean deleteByUserId(long id);
    //Optional<ShoppingCart> findByIdandUserId(long cartId, long userId);
    //Boolean save(ShoppingCart cart);

}