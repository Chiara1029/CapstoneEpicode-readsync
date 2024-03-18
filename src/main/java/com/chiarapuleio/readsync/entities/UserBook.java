package com.chiarapuleio.readsync.entities;

import com.chiarapuleio.readsync.enums.BookStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_books")
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public UserBook(User user, Book book, LocalDate startDate, LocalDate endDate, BookStatus bookStatus) {
        this.user = user;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookStatus = bookStatus;
    }
}
