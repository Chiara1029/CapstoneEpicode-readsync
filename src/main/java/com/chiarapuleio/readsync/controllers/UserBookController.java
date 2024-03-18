package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.UserBook;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.UserBookDTO;
import com.chiarapuleio.readsync.repositories.UserBookDAO;
import com.chiarapuleio.readsync.services.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userBooks")
public class UserBookController {

    @Autowired
    private UserBookService userBookSrv;
    @Autowired
    private UserBookDAO userBookDAO;

    @GetMapping
    public Page<UserBook>getAllUserBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort){
        return userBookSrv.getAllUserBooks(page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public UserBook saveUserBook(@RequestBody UserBookDTO userBook){
        return userBookSrv.saveUserBook(userBook);
    }

    @DeleteMapping("/{userBookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUserBook(@PathVariable Long userBookId){
        UserBook found = userBookDAO.findById(userBookId).orElseThrow(() -> new NotFoundException("Id " + userBookId + " not found."));
        if(found!= null){
            userBookDAO.delete(found);
        }
    }
}
