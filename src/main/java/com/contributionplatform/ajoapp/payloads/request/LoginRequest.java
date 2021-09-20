package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
