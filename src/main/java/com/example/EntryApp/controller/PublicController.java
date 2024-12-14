package com.example.EntryApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.service.UserDetailServiceImpl;
import com.example.EntryApp.service.UserService;
import com.example.EntryApp.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
	
	@PostMapping("/signup")
	public void signup(@RequestBody UserEntity user) {
		userService.saveNewUser(user);
	}
	
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody UserEntity user) {
	        try{
	        	System.out.println("user : "+user.getUserName()+" pasword : "+user.getPassword());
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
	            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
	            String jwt = jwtUtil.generateToken(userDetails.getUsername());
	            return new ResponseEntity<>(jwt, HttpStatus.OK);
	        }catch (Exception e){
	        	System.out.println("user : "+user.getUserName()+" pasword : "+user.getPassword());
	            log.error("Exception occurred while createAuthenticationToken ", e);
	            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
	        }
	 }
}
