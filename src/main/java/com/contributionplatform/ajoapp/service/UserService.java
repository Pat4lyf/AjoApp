package com.contributionplatform.ajoapp.service;

import com.contributionplatform.ajoapp.models.Contributions;
import com.contributionplatform.ajoapp.models.Requests;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User findUserByEmailAddress(String emailAddress);

    User getUser();

    ResponseEntity<?> doLogin(LoginRequest userRequest);

    ResponseEntity<SignUpResponse> register(SignUpRequest signUpRequest);

    ResponseEntity<SignUpResponse> editMemberDetails(UpdateRequest updateRequest);

    ResponseEntity<Response> requestToJoinACycle(Requests request, Long contributionCycleId);

    ResponseEntity<Response> requestToDeleteAccount();

    List<User> getCycleMembers(Long contributionCycleId) throws Exception;

    List<Contributions> getAllContributions(Long contributionCycleId) throws Exception;

    ResponseEntity<Response> makePayment(Contributions contribution, Long contributionCycleId);





}
