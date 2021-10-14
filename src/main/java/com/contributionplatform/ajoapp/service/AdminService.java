package com.contributionplatform.ajoapp.service;

import com.contributionplatform.ajoapp.models.ContributionCycle;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.ContributionCycleRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.ContributionCycleResponse;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<ContributionCycleResponse> createContributionCycle(ContributionCycleRequest contributionCycleRequest);

    ContributionCycle getActiveContributionCycle();

    ContributionCycle getContributionCycleById(Long id);

    ResponseEntity<SignUpResponse> register(SignUpRequest signUpRequest);

    ResponseEntity<SignUpResponse> createNewAdminAccount(SignUpRequest signUpRequest);

    ResponseEntity<SignUpResponse> editMemberDetails(Long id, UpdateRequest updateRequest);

    ResponseEntity<ContributionCycleResponse> editCycleDetails(UpdateRequest updateRequest);

    ResponseEntity<Response> deleteCycle();

    ResponseEntity<Response> addMemberToACycle();



}
