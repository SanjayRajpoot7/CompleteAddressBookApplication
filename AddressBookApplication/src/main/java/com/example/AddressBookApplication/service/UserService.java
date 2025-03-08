package com.example.AddressBookApplication.service;

import com.example.AddressBookApplication.dto.UserDTO;
import com.example.AddressBookApplication.model.User;
import com.example.AddressBookApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Add a new contact
    public User addContact(UserDTO contactDTO) {
        User contact = new User();
        contact.setFullName(contactDTO.getFullName());
        contact.setAddress(contactDTO.getAddress());
        contact.setCity(contactDTO.getCity());
        contact.setState(contactDTO.getState());
        contact.setZip(contactDTO.getZip());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());

        return userRepository.save(contact); // Store in MySQL
    }

    // Get all contacts
    public List<User> getAllContacts() {
        return userRepository.findAll();
    }

    // Get contact by ID
    public Optional<User> getContactById(Long id) {
        return userRepository.findById(id);
    }

    // Update contact by ID
    public User updateContact(Long id, UserDTO contactDTO) {
        Optional<User> existingContact = userRepository.findById(id);
        if (existingContact.isPresent()) {
            User contact = existingContact.get();
            contact.setFullName(contactDTO.getFullName());
            contact.setAddress(contactDTO.getAddress());
            contact.setCity(contactDTO.getCity());
            contact.setState(contactDTO.getState());
            contact.setZip(contactDTO.getZip());
            contact.setPhoneNumber(contactDTO.getPhoneNumber());

            return userRepository.save(contact); // Update in MySQL
        } else {
            throw new RuntimeException("Contact not found with ID: " + id);
        }
    }

    // Delete contact by ID
    public boolean deleteContact(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true; // Successfully deleted
        }
        return false; // Contact not found
    }

    public User saveAddress(User userService) {
        return userRepository.save(userService);  // Save the address to the database
    }
}