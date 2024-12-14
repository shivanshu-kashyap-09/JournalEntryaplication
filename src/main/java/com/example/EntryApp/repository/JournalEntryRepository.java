package com.example.EntryApp.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.EntryApp.entity.JournalEntry;


public interface JournalEntryRepository extends MongoRepository<JournalEntry , ObjectId> {

}
 