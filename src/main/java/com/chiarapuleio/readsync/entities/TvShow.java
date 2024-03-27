package com.chiarapuleio.readsync.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("TV_SHOW")
public class TvShow extends MediaItem{
    private int seasons;
    private String network;

    public TvShow(String title, int year, String plot, String poster, String genre, Book book, int seasons, String network) {
        super(title, year, plot, poster, genre, book);
        this.seasons = seasons;
        this.network = network;
    }
}
