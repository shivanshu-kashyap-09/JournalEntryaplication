package com.example.EntryApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EntryApp.entity.JournalEntry;
import com.example.EntryApp.entity.UserEntity;
import com.example.EntryApp.service.JournalEntryServices;
import com.example.EntryApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControl {

	@Autowired
	private JournalEntryServices journalEntryService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getAllJournalEntryOfUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
		UserEntity user = userService.findByUserName(userName);
		List<JournalEntry> allJournalEntry = user.getJournalEntries();
		if (allJournalEntry != null && !allJournalEntry.isEmpty()) {
			return new ResponseEntity<>(allJournalEntry, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	String userName = authentication.getName();
			journalEntryService.saveEntry(myEntry,userName);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/id/{myId}")
	public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
		System.out.println(myId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
		UserEntity user = userService.findByUserName(userName);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId() == myId ).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
			if (journalEntry.isPresent()) {
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
		boolean removed = journalEntryService.deleteOneById(myId,userName);
		if(removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId,
			@RequestBody JournalEntry newEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
    	UserEntity user = userService.findByUserName(userName);
    	List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId() == myId ).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
			if (journalEntry.isPresent()) {
				JournalEntry oldEntry = journalEntry.get();
				oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
				oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getContent().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
				journalEntryService.saveEntry(oldEntry);
				return new ResponseEntity<>(oldEntry, HttpStatus.OK);
		    }
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
