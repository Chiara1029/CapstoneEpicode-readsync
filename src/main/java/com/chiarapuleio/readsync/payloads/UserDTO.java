package com.chiarapuleio.readsync.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotEmpty
        String username,
        @NotEmpty
        String name,
        @NotEmpty
        String lastName,
        @NotEmpty
        @Email
        @Pattern(regexp = ".+@.+\\..+", message = "Invalid Email")
        String email,
        @NotEmpty
        String password
) {
}
