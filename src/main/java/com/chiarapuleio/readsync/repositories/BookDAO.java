package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.Book;
import com.chiarapuleio.readsync.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDAO extends JpaRepository<Book, Long> {
    boolean existsByIsbnCode(String isbnCode);

    Optional<Book> findByIsbnCode(String isbnCode);
}
