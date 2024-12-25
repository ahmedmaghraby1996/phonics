package com.example.phonics.repository;

import com.example.phonics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {


    User findUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.phone = ?1")
    User findUserByPhone(String phone);
}


