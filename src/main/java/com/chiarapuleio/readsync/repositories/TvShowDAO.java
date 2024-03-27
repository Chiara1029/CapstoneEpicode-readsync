package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvShowDAO extends JpaRepository<TvShow, Long> {
    List<TvShow> findByBookIsbnCode(String isbnCode);
}
