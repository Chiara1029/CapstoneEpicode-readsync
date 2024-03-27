package com.chiarapuleio.readsync.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("MOVIE")
public class Movie extends MediaItem{
    private String director;
    private String distributor;


    public Movie(String title, int year, String plot, String poster, String genre, Book book, String director, String distributor) {
        super(title, year, plot, poster, genre, book);
        this.director = director;
        this.distributor = distributor;
    }
}
