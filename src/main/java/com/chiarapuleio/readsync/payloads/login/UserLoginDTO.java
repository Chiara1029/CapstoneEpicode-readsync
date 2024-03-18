package com.chiarapuleio.readsync.payloads.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(
        @NotEmpty
        @Email
        @Pattern(regexp = ".+@.+\\..+", message = "Invalid Email")
        String email,
        @NotEmpty
        String password) {
}
