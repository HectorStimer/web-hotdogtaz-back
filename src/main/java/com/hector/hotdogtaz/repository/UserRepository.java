package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByActiveTrue();

    boolean existsByEmail(String email);
}
