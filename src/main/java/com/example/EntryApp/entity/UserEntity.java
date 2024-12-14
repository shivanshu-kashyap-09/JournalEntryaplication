package com.example.EntryApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity {

	@Id
	private ObjectId  id;
	
	@Indexed(unique = true)
	@NonNull
	private String userName;
	
	@NonNull
	private String password;
	 
	private String email;
	
	@DBRef 
	private List<JournalEntry> journalEntries = new ArrayList<>();
	
	 private List<String> roles;
	
}
