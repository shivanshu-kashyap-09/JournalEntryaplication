package com.example.EntryApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.EntryApp.entity.UserEntity;

public class UserRepositoryImpl {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<UserEntity> getUserForSA(){
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(Criteria.where("email").exists(true));
		query.addCriteria(Criteria.where("email").ne(null).ne("")); 
		
		//replace above both email lines with below singal line
		
//		query.addCriteria(Criteria.where("email").regex("pass the email valid expression "));		
		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
		
//		query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),
//		Criteria.where("email").exists(true)));
		
		List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
		return users;
	}
	
	

}
