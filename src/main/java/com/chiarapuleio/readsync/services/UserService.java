package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Review;
import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.entities.UserBook;
import com.chiarapuleio.readsync.exceptions.BadRequestException;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.UserDTO;
import com.chiarapuleio.readsync.repositories.UserDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;

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

    public User uploadAvatar(UUID id, MultipartFile file) throws IOException {
        User found = this.findById(id);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatarURL);
        return userDAO.save(found);
    }

    public List<Review> findReviewsByUserId(UUID userId){
        return userDAO.findReviewsByUserId(userId);
    }

    public List<UserBook> findReadBooksByUserId(UUID userId){
        return userDAO.findReadBooksByUserId(userId);
    }
    public List<UserBook> findToReadBooksByUserId(UUID userId){
        return userDAO.findToReadBooksByUserId(userId);
    }
    public List<UserBook> findCurrentlyReadingBooksByUserId(UUID userId){
        return userDAO.findCurrentlyReadingBooksByUserId(userId);
    }
}
