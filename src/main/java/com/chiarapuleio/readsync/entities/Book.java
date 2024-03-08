package com.chiarapuleio.readsync.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private String author;
    private String isbnCode;
    private String plot;
    private String genre;
    private String cover;
    //lista reviews
    //lista media


    public Book(String title, String author, String isbnCode, String plot, String genre, String cover) {
        this.title = title;
        this.author = author;
        this.isbnCode = isbnCode;
        this.plot = plot;
        this.genre = genre;
        this.cover = cover;
    }
}
