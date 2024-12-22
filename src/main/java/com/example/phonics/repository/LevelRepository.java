package com.example.phonics.repository;

import com.example.phonics.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository  extends JpaRepository<Level,Long> {
    Level findByTitle(String title);

}
