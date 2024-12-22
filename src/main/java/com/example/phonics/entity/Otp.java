package com.example.phonics.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "otp")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Otp extends AuditableEntity {


    private String code;

    private String userName;


    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    private OtpType otpType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
