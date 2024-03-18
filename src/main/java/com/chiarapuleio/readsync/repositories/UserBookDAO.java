package com.chiarapuleio.readsync.repositories;

import com.chiarapuleio.readsync.entities.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookDAO extends JpaRepository<UserBook, Long> {
}
