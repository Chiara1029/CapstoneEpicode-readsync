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
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public List<UserBook> getAllBooks(){
        return userBookDAO.findAll();
    }

    public UserBook saveUserBook(UserBookDTO userBook) {
        Book book = bookDAO.findByIsbnCode(userBook.isbnCode())
                .orElseThrow(() -> new NotFoundException(userBook.isbnCode()));
        User user = userDAO.findById(userBook.userId())
                .orElseThrow(() -> new NotFoundException(userBook.userId()));
        BookStatus bookStatus = BookStatus.valueOf(userBook.bookStatus());
        LocalDate startDate = userBook.startDate();
        if (bookStatus == BookStatus.CURRENTLY_READING && startDate == null) {
            startDate = LocalDate.now();
        }
        UserBook newUserBook = new UserBook(user, book, startDate, userBook.endDate(), bookStatus);
        return userBookDAO.save(newUserBook);
    }

    public void delete(Long id){
        UserBook userBook = userBookDAO.findById(id).orElseThrow(()-> new NotFoundException(String.valueOf(id)));
        userBookDAO.delete(userBook);
    }

    public UserBook findByIdAndUpdate(Long id, UserBookDTO updatedUserBook){
        UserBook foundUserBook = userBookDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("UserBook ID not found: " + id));
        Book book = bookDAO.findByIsbnCode(updatedUserBook.isbnCode())
                .orElseThrow(() -> new NotFoundException("Book not found: " + updatedUserBook.isbnCode()));
        User user = userDAO.findById(updatedUserBook.userId())
                .orElseThrow(() -> new NotFoundException("User not found: " + updatedUserBook.userId()));
        foundUserBook.setBook(book);
        foundUserBook.setUser(user);
        foundUserBook.setStartDate(updatedUserBook.startDate());
        foundUserBook.setEndDate(updatedUserBook.endDate());
        foundUserBook.setBookStatus(BookStatus.valueOf(updatedUserBook.bookStatus()));

        return userBookDAO.save(foundUserBook);
    }
}
