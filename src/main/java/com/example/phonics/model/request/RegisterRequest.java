package com.example.phonics.model.request;



import com.example.phonics.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder

public class RegisterRequest {


  @NotBlank(message = "Name is mandatory")
  private String firstname;
  private String lastname;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;
  private String phone;
  private String password;
  private String confirmPassword;
  private Role role;
}
