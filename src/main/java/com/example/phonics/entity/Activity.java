package com.example.phonics.entity;

import com.example.phonics.entity.enums.ActivityType;
import com.example.phonics.entity.enums.LevelType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "activity")
@Data
public class Activity  extends  AuditableEntity{

    private String image;
    private  String answer;
    private String sound;

    private String word;


    private String choices;


    private String missingLetters;
    @Transient
    private boolean isCompleted;



    @OneToMany
    @JoinTable(
            name = "activity_users", // Name of the join table
            joinColumns = @JoinColumn(name = "activity_id"), // Column in the join table referencing Activity
            inverseJoinColumns = @JoinColumn(name = "user_id") // Column in the join table referencing User
    )
    @JsonIgnore
    private List<User> users;


    @Column(columnDefinition = "ENUM('MissingLetter', 'LongShort') DEFAULT 'MissingLetter'")
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    @JsonIgnore
    private Level level;

}
