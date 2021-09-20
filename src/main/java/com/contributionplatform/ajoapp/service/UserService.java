package com.contributionplatform.ajoapp.service;

import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findUserByEmailAddress(String emailAddress);

    ResponseEntity<?> doLogin(LoginRequest userRequest);

    ResponseEntity<SignUpResponse> register(SignUpRequest signUpRequest);

    ResponseEntity<SignUpResponse> createNewAdminAccount(SignUpRequest signUpRequest);

    ResponseEntity<SignUpResponse> editMemberDetails(UpdateRequest updateRequest);

}
