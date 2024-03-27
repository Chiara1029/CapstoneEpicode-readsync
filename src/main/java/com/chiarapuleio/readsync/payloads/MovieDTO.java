package com.chiarapuleio.readsync.payloads;

public record MovieDTO(
        String title,
        int year,
        String plot,
        String poster,
        String genre,
        String bookIsbn,
        String director,
        String distributor
) {
}