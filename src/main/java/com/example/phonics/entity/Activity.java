package com.example.phonics.entity;

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

    private List<String> word;


    private List<String> choices;


    private List<String> missingLetters;
    @Transient
    private boolean isCompleted;

    @OneToMany
    @JsonIgnore
    private  List<User> users;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    @JsonIgnore
    private Level level;

}
