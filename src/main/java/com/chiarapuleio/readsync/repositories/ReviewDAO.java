package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDAO extends JpaRepository<Review, Long> {
}
