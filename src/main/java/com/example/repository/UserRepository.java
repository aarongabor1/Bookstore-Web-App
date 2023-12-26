package com.example.repository;

import com.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
    //Optional<User> findById(Long userId);
    //Optional<User> findByEmail(String email); //not sure what datatype or where the email is coming from, just put string for now
    //Boolean save(User user);
    //Boolean deleteById(long id);
    //Boolean existsByEmail(String email);
}
