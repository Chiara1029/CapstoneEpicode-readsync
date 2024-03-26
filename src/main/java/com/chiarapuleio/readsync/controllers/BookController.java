package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.BookDTO;
import com.chiarapuleio.readsync.repositories.BookDAO;
import com.chiarapuleio.readsync.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookSrv;
    @Autowired
    private BookDAO bookDAO;

    @GetMapping
    public Page<Book> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort){
        return bookSrv.getAllBooks(page, size, sort);
    }

    @GetMapping("/{isbnCode}")
    public Book getBookByIsbn(@PathVariable String isbnCode){
        return bookSrv.findByIsbnCode(isbnCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book saveBook(@RequestBody BookDTO newBook){
        return bookSrv.save(newBook);
    }

    @DeleteMapping("/{isbnCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeBook(@PathVariable String isbnCode){
        Book found = bookSrv.findByIsbnCode(isbnCode);
        if(found != null && found.getReviewList().isEmpty()){
            bookDAO.delete(found);
        } else throw new NotFoundException(isbnCode + " not found.");
    }

    @PutMapping("/{isbnCode}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book findByIsbnAndUpdate(@PathVariable String isbnCode, @RequestBody BookDTO updatedBook){
        return bookSrv.findByIsbnCodeAndUpdate(isbnCode, updatedBook);
    }
}
