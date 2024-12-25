package com.example.phonics.controller;


import com.example.phonics.exception.PasswordMismatchException;
import com.example.phonics.exception.NotFoundException;
import com.example.phonics.model.request.*;
import com.example.phonics.model.response.ActionResponse;
import com.example.phonics.model.response.LoginRes;
import com.example.phonics.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "this is the documentation for auth apis")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping(value = "/send-otp")
    public ActionResponse<String> login(@RequestBody SendOtpRequest otpRequest) {
        return new ActionResponse<String>( authService.sendOtp(otpRequest));
    }

    @PostMapping(value = "/verify-otp")
    public ActionResponse<LoginRes> login(@RequestBody VerifyOtpRequest otpRequest) {
        return new ActionResponse(authService.verifyOtp(otpRequest));
    }

    @Operation(summary = "login a user",
            responses = {
                    @ApiResponse(description = "add new item to the database", responseCode = "202",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginRes.class))),
                    @ApiResponse(description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class)))
            })
    @PostMapping(value = "/login")
    public ActionResponse<LoginRes> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("This sentence will work!");
        return new ActionResponse<>(authService.login(loginRequest));
    }

    @Operation(summary = "login a user",
            responses = {
                    @ApiResponse(description = "login a user and return the user token", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginRes.class))),
                    @ApiResponse(description = "User not found", responseCode = "404")
            })


    @PostMapping("/register")
    public ActionResponse<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest)  {


        return new ActionResponse(authService.register(registerRequest));
    }

}