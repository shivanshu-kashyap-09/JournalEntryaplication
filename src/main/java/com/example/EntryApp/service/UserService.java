package com.example.EntryApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	 private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public void saveEntry(UserEntity user) {
		userRepository.save(user);	
	}
	
	public boolean saveNewUser(UserEntity user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			
			return true;			
		}catch(Exception e) {
            log.error("error occured for {}",user.getUserName(),e);
            return false;
		}
	}
	
	public boolean saveAdmin(UserEntity user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER","ADMIN"));
			userRepository.save(user);
			return true;			
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<UserEntity> getAll(){
		return userRepository.findAll();
	}
	
	public Optional<UserEntity> findById(ObjectId myId){
		return userRepository.findById(myId);
	}
	
	public void deleteById(ObjectId myId) {
		userRepository.deleteById(myId); 
	}
	
	public UserEntity findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
}
