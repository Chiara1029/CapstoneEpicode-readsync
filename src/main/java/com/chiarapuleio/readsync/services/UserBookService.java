package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.entities.UserBook;
import com.chiarapuleio.readsync.enums.BookStatus;
import com.chiarapuleio.readsync.exceptions.BadRequestException;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.UserBookDTO;
import com.chiarapuleio.readsync.repositories.BookDAO;
import com.chiarapuleio.readsync.repositories.UserBookDAO;
import com.chiarapuleio.readsync.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserBookService {
    @Autowired
    private UserBookDAO userBookDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private UserDAO userDAO;

    public Page<UserBook> getAllUserBooks(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userBookDAO.findAll(pageable);
    }

    public UserBook saveUserBook(UserBookDTO userBook) {
        Book book = bookDAO.findByIsbnCode(userBook.isbnCode())
                .orElseThrow(() -> new NotFoundException(userBook.isbnCode()));
        User user = userDAO.findById(userBook.userId())
                .orElseThrow(() -> new NotFoundException(userBook.userId()));

        BookStatus bookStatus;

        if (userBook.startDate() != null && userBook.endDate() != null) {
            bookStatus = BookStatus.READ;
        } else if (userBook.startDate() != null && userBook.endDate() == null) {
            bookStatus = BookStatus.CURRENTLY_READING;
        } else if (userBook.startDate() == null && userBook.endDate() == null) {
            bookStatus = BookStatus.TO_READ;
        } else {
            throw new BadRequestException("Invalid Input.");
        }

        UserBook newUserBook = new UserBook(user, book, userBook.startDate(), userBook.endDate(), bookStatus);

        return userBookDAO.save(newUserBook);
    }


    public void delete(Long id){
        UserBook userBook = userBookDAO.findById(id).orElseThrow(()-> new NotFoundException(String.valueOf(id)));
        userBookDAO.delete(userBook);
    }
}
