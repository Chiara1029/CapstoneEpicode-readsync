package com.chiarapuleio.readsync.payloads;

import com.chiarapuleio.readsync.enums.BookStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UserBookDTO (
        UUID userId,
        String isbnCode,
        LocalDate startDate,
        LocalDate endDate,
        String bookStatus
){
}
