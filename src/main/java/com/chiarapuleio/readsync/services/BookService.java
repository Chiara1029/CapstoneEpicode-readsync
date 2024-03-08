package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.exceptions.BadRequestException;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.BookDTO;
import com.chiarapuleio.readsync.repositories.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    public Page<Book> getAllBooks(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return bookDAO.findAll(pageable);
    }

    public Book save(BookDTO book){
        if(bookDAO.existsByIsbnCode(book.isbnCode())) throw new BadRequestException("This ISBN already exists.");
        Book newBook = new Book(book.title(), book.author(), book.isbnCode(), book.plot(), book.genre(), book.cover());
        return bookDAO.save(newBook);
    }

    public Book findByIsbnCode(String isbnCode){
        return bookDAO.findByIsbnCode(isbnCode).orElseThrow(() -> new NotFoundException(isbnCode + " not found."));
    }


}
