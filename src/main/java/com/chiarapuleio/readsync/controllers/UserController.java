package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.Review;
import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.entities.UserBook;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.exceptions.UnauthorizedException;
import com.chiarapuleio.readsync.repositories.UserDAO;
import com.chiarapuleio.readsync.security.JWTTools;
import com.chiarapuleio.readsync.services.AuthService;
import com.chiarapuleio.readsync.services.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userSrv;
    @Autowired
    private AuthService authSrv;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/me")
    public UUID getCurrentUserId(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        jwtTools.verifyToken(token);
        String userId = jwtTools.extractIdFromToken(token);
        return UUID.fromString(userId);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId){
        return userSrv.findById(userId);
    }

    @PatchMapping("/{id}/avatar")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID id) {
        try {
            return userSrv.uploadAvatar(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}/reviews")
    public List<Review> getUserReviews(@PathVariable UUID userId){
        return userSrv.findReviewsByUserId(userId);
    }

    @GetMapping("/{userId}/read")
    public List<UserBook> getUserReadBooks(@PathVariable UUID userId){
        return userSrv.findReadBooksByUserId(userId);
    }

    @GetMapping("/{userId}/toRead")
    public List<UserBook> getUserToReadBooks(@PathVariable UUID userId){
        return userSrv.findToReadBooksByUserId(userId);
    }

    @GetMapping("/{userId}/currentlyReading")
    public List<UserBook> getUserCurrentlyReadingBooks(@PathVariable UUID userId){
        return userSrv.findCurrentlyReadingBooksByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUser(@PathVariable UUID userId){
        User found = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        if(found != null){
            userSrv.delete(userId);
        }
    }

}
