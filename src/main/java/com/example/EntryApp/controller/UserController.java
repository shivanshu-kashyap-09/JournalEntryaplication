package com.example.EntryApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.repository.UserRepository;
import com.example.EntryApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
        UserEntity userInDb = userService.findByUserName(userName);
    	
        userInDb.setUserName(userEntity.getUserName());
        userInDb.setPassword(userEntity.getPassword());
        userService.saveNewUser(userInDb);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	userRepository.deleteByUserName(authentication.getName());
    	 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
