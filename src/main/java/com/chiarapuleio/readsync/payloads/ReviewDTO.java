package com.chiarapuleio.readsync.payloads;

import java.util.UUID;

public record ReviewDTO(
        String title,
        String body,
        UUID userId,
        String bookIsbn
) {
}
