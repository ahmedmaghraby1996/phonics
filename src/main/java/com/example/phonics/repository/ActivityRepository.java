package com.example.phonics.repository;

import com.example.phonics.entity.Activity;
import com.example.phonics.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository  extends JpaRepository<Activity, Long> {
    List<Activity> findByLevelId(Long levelId);
}
