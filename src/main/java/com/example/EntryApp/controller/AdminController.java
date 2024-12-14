package com.example.EntryApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/all-user")
	public ResponseEntity<?> getAllUsers(){
		List<UserEntity> all = userService.getAll();
		if(all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/admin-create")
	public void createAdmin(@RequestBody UserEntity user) {
		userService.saveAdmin(user);
	}
}
