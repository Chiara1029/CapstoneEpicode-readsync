package com.chiarapuleio.readsync.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "media_items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "media_type", discriminatorType = DiscriminatorType.STRING)
public abstract class MediaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private int year;
    private String plot;
    private String poster;
    private String genre;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"reviewList"})
    private Book book;


    public MediaItem(String title, int year, String plot, String poster, String genre, Book book) {
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.poster = poster;
        this.genre = genre;
        this.book = book;
    }
}
