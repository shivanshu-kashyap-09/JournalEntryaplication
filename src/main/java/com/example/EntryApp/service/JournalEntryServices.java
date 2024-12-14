package com.example.EntryApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EntryApp.entity.JournalEntry;
import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.repository.JournalEntryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalEntryServices {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;

	@Transactional 
	public void saveEntry(JournalEntry journalEntry, String userName) {
		try {
			
			UserEntity user = userService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(saved);
			userService.saveEntry(user);
		}catch(Exception e) {
			throw new RuntimeException("an error occured while saving the entry",e);
		}
	
	}
	
	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAllEntry(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> getOneById(ObjectId myId) {
		return journalEntryRepository.findById(myId);
	}
	
	@Transactional
	public boolean deleteOneById(ObjectId myId,String userName) {
		boolean removed = false;
		try {
			UserEntity user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId() == myId);
			if(removed) {
				userService.saveEntry(user);
				journalEntryRepository.deleteById(myId);
			}	
		}catch(Exception e) {
			log.error("Error ",e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
		}
		
		return removed;
	}
	
	public Optional<JournalEntry> findById(ObjectId myId){
		return journalEntryRepository.findById(myId);
	}
}
