package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotNull(message = "first name cannot be empty")
    private String firstName;

    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String emailAddress;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String password;

    private String phoneNumber;

}
