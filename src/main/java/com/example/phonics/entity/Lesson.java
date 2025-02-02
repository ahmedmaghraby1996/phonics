package com.example.phonics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lesson")
@Data

public class Lesson extends AuditableEntity {
    private String title;
    private String image;
    private  String name;
    private String sound;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    @JsonIgnore
    private Level level;

    @Transient
    private boolean isCompleted;




    @OneToMany
    @JoinTable(
            name = "lesson_users", // Name of the join table
            joinColumns = @JoinColumn(name = "lesson_id"), // Column in the join table referencing Activity
            inverseJoinColumns = @JoinColumn(name = "user_id") // Column in the join table referencing User
    )
    @JsonIgnore
    private List<User> users;


}
