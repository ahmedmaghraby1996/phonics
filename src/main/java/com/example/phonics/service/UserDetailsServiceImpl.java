package com.example.phonics.service;


import com.example.phonics.entity.User;
import com.example.phonics.model.request.UpdateProfileRequest;
import com.example.phonics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));

        // Assign roles correctly
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));



        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getPassword(), // Hashed password
                authorities // Roles
        );
    }


    public   UpdateProfileRequest getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setEmail(user.getEmail());
        request.setFirstName(user.getFirstName());
return request;
    }
    public UpdateProfileRequest updateProfile(UpdateProfileRequest request) {
        if (request != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }

            userRepository.save(user);
        }
        return request;
    }

    public boolean deleteAccount(){
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userRepository.delete(user);
        return true;}
        catch (Error e){return  false;}
    }
}


