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

    @OneToMany
    @JoinTable(
            name = "level_users", // Name of the join table
            joinColumns = @JoinColumn(name = "level_id"), // Column in the join table referencing Activity
            inverseJoinColumns = @JoinColumn(name = "user_id") // Column in the join table referencing User
    )
    @JsonIgnore
    private List<User> users;
    @Transient
    private boolean isCompleted;
    @Transient
    private boolean isTracingCompleted;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('LEVEL', 'LETTER')")
    private LevelType type;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Activity> activities;
}
