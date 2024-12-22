package com.example.phonics.model.request;

import com.example.phonics.entity.OtpType;
import lombok.Data;

@Data
public class SendOtpRequest {
    private String userName;
    private OtpType otpType;
}

