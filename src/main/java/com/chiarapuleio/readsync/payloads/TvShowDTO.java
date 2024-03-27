package com.chiarapuleio.readsync.payloads;

public record TvShowDTO(
        String title,
        int year,
        String plot,
        String poster,
        String genre,
        String bookIsbn,
        int seasons,
        String network
) {
}