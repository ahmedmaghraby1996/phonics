package com.example.phonics.repository;

import com.example.phonics.entity.Level;
import com.example.phonics.entity.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LevelRepository  extends JpaRepository<Level,Long> {

    ArrayList<Level> findByType(LevelType type);
    Level findByTitle(String title);

}
