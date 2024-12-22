package com.example.phonics.model.request;

import com.example.phonics.entity.OtpType;
import lombok.Data;

@Data
public class VerifyOtpRequest {
    private  String userName;
    private  String code;
    private OtpType otpType;
}
