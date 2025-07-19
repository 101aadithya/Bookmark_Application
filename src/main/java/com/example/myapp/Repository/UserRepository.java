package com.example.myapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}