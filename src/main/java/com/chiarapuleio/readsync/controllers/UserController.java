package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.services.AuthService;
import com.chiarapuleio.readsync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userSrv;
    @Autowired
    private AuthService authSrv;
}
