package com.example.phonics.entity;


import com.example.phonics.entity.enums.LevelType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "level")
@Data
public class Level extends AuditableEntity {



    @Column(unique = true)
    private String title;

    private String logo;



    private  String lesson_image;

    private  String letter_image;
    private  String letter_sound;

    @Enumerated(EnumType.STRING)
    private LevelType type;




    private String description;

    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Tracing> tracings;

    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Activity> activities;
}
