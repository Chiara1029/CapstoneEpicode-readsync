package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.Review;
import com.chiarapuleio.readsync.entities.User;
import com.chiarapuleio.readsync.entities.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT u.reviewList FROM User u WHERE u.id = :userId")
    List<Review> findReviewsByUserId(@Param("userId") UUID userId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.bookStatus = 'READ'")
    List<UserBook> findReadBooksByUserId(@Param("userId") UUID userId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.bookStatus = 'TO_READ'")
    List<UserBook> findToReadBooksByUserId(@Param("userId") UUID userId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.bookStatus = 'CURRENTLY_READING'")
    List<UserBook> findCurrentlyReadingBooksByUserId(@Param("userId") UUID userId);
}
