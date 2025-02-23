package com.example.phonics.model.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UpdateProfileRequest {
   private String FirstName;
    @Email(message = "Email should be valid")
    private String email;
}
