package com.example.phonics.entity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Data
public class AuditableEntity extends  BaseEntity {



        @Column(name = "created_at", updatable = false)
        private Date createdAt;

        @Column(name = "updated_at")
        private Date updatedAt;

        @Column(name = "deleted_at")
        private Date deletedAt;

        // Other fields, getters, and setters

        @PrePersist
        protected void onCreate() {
            createdAt = new Date();
            updatedAt = new Date();
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = new Date();
        }

        // Getters and Setters
    }


