package com.example.phonics.service;


import com.example.phonics.entity.Otp;
import com.example.phonics.entity.OtpType;
import com.example.phonics.entity.User;

import com.example.phonics.exception.BadRequestException;
import com.example.phonics.exception.DuplicateEntryException;
import com.example.phonics.exception.PasswordMismatchException;
import com.example.phonics.exception.NotFoundException;
import com.example.phonics.model.request.*;
import com.example.phonics.model.response.ActionResponse;
import com.example.phonics.model.response.LoginRes;
import com.example.phonics.repository.OtpRepository;
import com.example.phonics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;


    public String sendOtp(SendOtpRequest request) {

        User user = new User();
        if (request.getOtpType().equals(OtpType.EMAIL)) {
            user = userRepository.findUserByEmail(request.getUserName());

            System.out.println(user);
        } else if (request.getOtpType().equals(OtpType.PHONE)) {
            user = userRepository.findUserByPhone(request.getUserName());
        }
        if (user == null) {
            throw new NotFoundException("the user does not exist");
        }
        Otp oldOtp = otpRepository.findOtpByUser(user);
        if (oldOtp != null) {
            otpRepository.delete(oldOtp);
        }
        Otp otp = new Otp();
        String code="1234";
        otp.setCode(code);
        otp.setUserName(request.getUserName());
        otp.setUser(user);
        otp.setOtpType(request.getOtpType());
        otp.setExpirationDate(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
        otpRepository.save(otp);
        return code;

    }

    public LoginRes verifyOtp(VerifyOtpRequest request) {
        Otp otp = otpRepository.findOtpByCode(request);
        if (otp == null)
            throw new NotFoundException("Wrong code");

        String jwtToken = "";

        long timeDiff = new Date().getTime() - otp.getCreatedAt().getTime();
        if (timeDiff < (5 * 60 * 1000)) {
            User user = request.getOtpType()==OtpType.EMAIL? userRepository.findUserByEmail(request.getUserName()):userRepository.findUserByPhone(request.getUserName());
            jwtToken = this.generateToken(user);
            user.setEnabled(true);
            userRepository.save(user);
            otpRepository.delete(otp);
        } else throw new BadRequestException("Otp Expired");

        return new LoginRes(jwtToken);

    }

    public LoginRes login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());
        //handle user not found exception
        if (user == null)
            throw new NotFoundException("the user does not exist");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), request.getPassword()));





        String jwtToken = this.generateToken(user);
//        saveUserToken(user, jwtToken);
        return new LoginRes(jwtToken);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new PasswordMismatchException("the password and confirmation password not equal");

        if (userRepository.findUserByEmail(request.getEmail()) != null) {
            throw new DuplicateEntryException("Email is already in use.");
        }
        if (userRepository.findUserByPhone(request.getPhone()) != null) {
            throw new DuplicateEntryException("Phone number is already in use.");
        }

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
//        Map<String , Object> extraClaims = new HashMap<>();
//        String jwtToken = jwtService.createToken(user , extraClaims);
//        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(user);
    }

    private String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        String jwtToken = jwtService.createToken(user, extraClaims);
        return jwtToken;
    }

}
