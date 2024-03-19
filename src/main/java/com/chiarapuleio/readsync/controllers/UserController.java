package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.exceptions.UnauthorizedException;
import com.chiarapuleio.readsync.security.JWTTools;
import com.chiarapuleio.readsync.services.AuthService;
import com.chiarapuleio.readsync.services.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
