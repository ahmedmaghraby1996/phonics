package com.example.phonics.controller;

import com.example.phonics.model.request.AuthenticationResponse;
import com.example.phonics.model.request.RegisterRequest;
import com.example.phonics.model.request.UpdateProfileRequest;
import com.example.phonics.model.response.ActionResponse;
import com.example.phonics.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @PutMapping("/profile")
    public ActionResponse<AuthenticationResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request)  {

        return new ActionResponse(userService.updateProfile(request));
    }
    @GetMapping("/profile")
    public ActionResponse<AuthenticationResponse> getProfile()  {

        return new ActionResponse(userService.getProfile());
    }
    @DeleteMapping("/profile")
    public ActionResponse<AuthenticationResponse> deleteProfile()  {

        return new ActionResponse(userService.deleteAccount());
    }

}
