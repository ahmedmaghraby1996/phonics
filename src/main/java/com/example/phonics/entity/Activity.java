package com.example.phonics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "activity")
@Data
public class Activity  extends  AuditableEntity{

    private String image;
    private  String solution;
    private String sound;
    private String task;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="level_id")
    @JsonIgnore
    private Level level;
}
