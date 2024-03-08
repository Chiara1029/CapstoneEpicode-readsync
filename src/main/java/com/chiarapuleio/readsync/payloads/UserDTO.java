package com.chiarapuleio.readsync.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(
        @NotEmpty
        String username,
        @NotEmpty
        String name,
        @NotEmpty
        String lastName,
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password
) {
}
