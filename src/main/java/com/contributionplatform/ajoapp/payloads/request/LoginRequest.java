package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "please enter your email")
    private String emailAddress;

    @NotBlank(message = "please enter your password")
    private String password;
}
