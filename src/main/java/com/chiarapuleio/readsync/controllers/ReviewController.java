package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.Review;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.ReviewDTO;
import com.chiarapuleio.readsync.repositories.ReviewDAO;
import com.chiarapuleio.readsync.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewSrv;
    @Autowired
    private ReviewDAO reviewDAO;

    @GetMapping
    public Page<Review> getAllReviews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
        return reviewSrv.getAllReviews(page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public Review saveReview(@RequestBody ReviewDTO newReview){
        return reviewSrv.save(newReview);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER')")
    public void removeReview(@PathVariable Long reviewId){
        Review found = reviewDAO.findById(reviewId).orElseThrow(()-> new NotFoundException(reviewId + " not found."));
        if(found != null){
            reviewDAO.delete(found);
        } else throw new NotFoundException(reviewId + " not found.");
    }
}
