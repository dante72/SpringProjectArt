package com.example.SpringProjectArt.repository;

import com.example.SpringProjectArt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
