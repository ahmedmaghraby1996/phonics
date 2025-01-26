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
    @ElementCollection // Maps the list directly
    @CollectionTable(name = "example_word", joinColumns = @JoinColumn(name = "word_id"))
    @Column(name = "word_value")
    private List<String> word;
    @ElementCollection // Maps the list directly
    @CollectionTable(name = "example_choice", joinColumns = @JoinColumn(name = "choice_id"))
    @Column(name = "choice_value")
    private List<String> choices;

    @CollectionTable(name = "example_missingLetters", joinColumns = @JoinColumn(name = "missingLetters_id"))
    @Column(name = "missingLetters_value")
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
