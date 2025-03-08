package com.example.AddressBookApplication.controller;

import com.example.AddressBookApplication.dto.UserDTO;
import com.example.AddressBookApplication.model.User;
import com.example.AddressBookApplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/addressbook")
public class UserController {
    @Autowired
    private UserService userService;
    // Get all contacts
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllContacts() {
        return ResponseEntity.ok(userService.getAllContacts());
    }

    // Create a new contact
    @PostMapping("/add")
    public ResponseEntity<User> addContact(@Valid @RequestBody UserDTO contactDTO) {
        return ResponseEntity.ok(userService.addContact(contactDTO));
    }


//    @PostMapping("/add")
//    public User addAddress(@RequestBody User addressBook) {
//        return userService.saveAddress(addressBook);  // Ensure this method is properly saving the data
//    }


    // Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        Optional<User> contact = userService.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update contact by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @Valid @RequestBody UserDTO contactDTO) {
        try {
            User updatedContact = userService.updateContact(id, contactDTO);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete contact by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        boolean isDeleted = userService.deleteContact(id);
        if (isDeleted) {
            return ResponseEntity.ok("Contact deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Contact not found.");
        }
    }
}