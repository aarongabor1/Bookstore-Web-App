package com.example.repository;

import com.example.model.Book;
import com.example.model.Purchase;
import com.example.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    //Optional<Purchase> findById(long purchaseId);
    Boolean deleteById(long id);
    List<Purchase> findByUserId(long id);
    List<Purchase> findByUser(User user);

    @Query("SELECT DISTINCT p.user FROM Purchase p WHERE p.user != :user")
    List<User> findAllUsersExcept(@Param("user") User user);

    @Query("SELECT b FROM Purchase p JOIN p.books b where p.user = :user")
    Set<Book> findBooksByUser(@Param("user") User user);
}
