package com.example.userservice.repository;

import com.example.userservice.model.User;
import jdk.dynalink.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByFirstName(String firstName);
    Optional<User> getByLastName(String lastName);

    boolean existsByFirstName(String firstName);
    boolean existsByLastName(String lastName);
}
