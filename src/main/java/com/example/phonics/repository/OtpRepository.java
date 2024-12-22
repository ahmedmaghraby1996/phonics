package com.example.phonics.repository;

import com.example.phonics.entity.Otp;
import com.example.phonics.entity.User;
import com.example.phonics.model.request.VerifyOtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepository extends JpaRepository<Otp,Integer> {
    @Query("SELECT o FROM Otp o WHERE o.user = :user")
    Otp findOtpByUser(User user);

    @Query("SELECT o FROM Otp o WHERE o.userName = :username")
    Otp findOtpByUsername(String username);

    @Query("SELECT o FROM Otp o WHERE o.userName = :#{#request.userName} AND o.code = :#{#request.code} AND o.otpType = :#{#request.otpType}")
    Otp findOtpByCode(@Param("request") VerifyOtpRequest request);


}
