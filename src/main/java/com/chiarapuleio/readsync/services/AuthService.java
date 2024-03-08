package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.enums.UserRole;
import com.chiarapuleio.readsync.exceptions.BadRequestException;
import com.chiarapuleio.readsync.exceptions.UnauthorizedException;
import com.chiarapuleio.readsync.payloads.UserDTO;
import com.chiarapuleio.readsync.payloads.login.UserLoginDTO;
import com.chiarapuleio.readsync.repositories.UserDAO;
import com.chiarapuleio.readsync.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UserService userSrv;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginDTO userLog) {
        User user = userSrv.findByEmail(userLog.email());
        if (bcrypt.matches(userLog.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials, try again.");
        }
    }

    public User save(UserDTO user) throws IOException {
        userDAO.findByEmail(user.email()).ifPresent(us -> {
            throw new BadRequestException("Email: " + user.email() + " already exist.");
        });
        User newUser = new User(user.username(), user.name(), user.lastName(), user.email(), bcrypt.encode(user.password()));
        newUser.setAvatar("https://ui-avatars.com/api/?name=" + user.name() + user.lastName());
        newUser.setUserRole(UserRole.USER);
        return userDAO.save(newUser);
    }
}
