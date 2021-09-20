package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class UpdateRequest {
    @NotBlank
    private String firstName;

    private String lastName;

    @Email(message = "must be email")
    private String emailAddress;

    @Size(min = 6, max = 24)
    private String password;

    private String phoneNumber;
}
