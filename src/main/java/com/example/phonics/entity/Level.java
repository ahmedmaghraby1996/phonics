package com.example.phonics.entity;


import com.example.phonics.entity.enums.LevelType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private  String lessonImage;
    private  String letterImage;
    private  String letterSound;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('LEVEL', 'LETTER')")
    private LevelType type;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Lesson> lessons;
    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Tracing> tracings;
    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Activity> activities;
}
