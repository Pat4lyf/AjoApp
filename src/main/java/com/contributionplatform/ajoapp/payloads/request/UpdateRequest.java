package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class UpdateRequest {
    @NotBlank(message = "first name cannot be empty")
    private String firstName;

    @NotBlank(message = "last name cannot be empty")
    private String lastName;

    @NotBlank(message = "email cannot be empty")
    @Email(message = "must be email")
    private String emailAddress;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String password;

    @NotBlank(message = "PhoneNumber cannot be empty")
    private String phoneNumber;
}
