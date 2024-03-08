package com.chiarapuleio.readsync.payloads;

public record BookDTO(
        String title,
        String author,
        String isbnCode,
        String plot,
        String genre,
        String cover) {
}
