package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.exceptions.BadRequestException;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.UserDTO;
import com.chiarapuleio.readsync.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public User save(UserDTO user){
        if(userDAO.existsByEmail(user.email())) throw new BadRequestException("This email already exist.");
        User newUser = new User(user.username(), user.name(), user.lastName(), user.email(), user.password());
        return userDAO.save(newUser);
    }

    public User findById(UUID id){
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id + " not found."));
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found."));
    }

}
