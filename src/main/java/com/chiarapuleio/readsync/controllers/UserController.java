package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.services.AuthService;
import com.chiarapuleio.readsync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PatchMapping("/{id}/avatar")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID id) {
        try {
            return userSrv.uploadAvatar(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
