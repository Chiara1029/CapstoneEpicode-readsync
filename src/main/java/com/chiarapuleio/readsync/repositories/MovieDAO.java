package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Long> {
    List<Movie> findByBookIsbnCode(String isbnCode);
}
