package com.example.EntryApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.EntryApp.entity.UserEntity;


public interface UserRepository extends MongoRepository<UserEntity,ObjectId>{
	
	UserEntity findByUserName(String userName);
	
	UserEntity deleteByUserName(String userName);

}
