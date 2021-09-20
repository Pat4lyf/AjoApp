package com.contributionplatform.ajoapp.payloads.response;

import com.contributionplatform.ajoapp.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private Roles role;

    public SignUpResponse(String userId, String firstName, String lastName, String emailAddress, Roles role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.role = role;
    }
}
