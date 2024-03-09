package com.chiarapuleio.readsync.services;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.Review;
import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.payloads.ReviewDTO;
import com.chiarapuleio.readsync.repositories.ReviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private BookService bookSrv;
    @Autowired
    private UserService userSrv;

    public Page<Review> getAllReviews(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return reviewDAO.findAll(pageable);
    }

    public Review save (ReviewDTO review){
        User user = userSrv.findById(review.userId());
        Book book = bookSrv.findByIsbnCode(review.bookIsbn());

        Review newReview = new Review(review.title(), review.body(), book, user);
        return reviewDAO.save(newReview);
    }
}
