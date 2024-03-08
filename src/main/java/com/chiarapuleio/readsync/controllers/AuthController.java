package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.payloads.UserDTO;
import com.chiarapuleio.readsync.payloads.login.UserLogRespDTO;
import com.chiarapuleio.readsync.payloads.login.UserLoginDTO;
import com.chiarapuleio.readsync.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authSrv;

    @PostMapping("/login")
    public UserLogRespDTO login(@RequestBody UserLoginDTO userLog){
        String accessToken = authSrv.authenticateUserAndGenerateToken(userLog);
        return new UserLogRespDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody UserDTO newUser) throws IOException {
        return this.authSrv.save(newUser);
    }
}