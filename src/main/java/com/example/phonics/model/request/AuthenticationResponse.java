package com.example.phonics.model.request;

import com.example.phonics.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private User user;
//  private String email;
}
