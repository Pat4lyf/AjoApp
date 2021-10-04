package com.contributionplatform.ajoapp.controllers;

import com.contributionplatform.ajoapp.models.Requests;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import com.contributionplatform.ajoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest userRequest){
        return userService.doLogin(userRequest);
    }

    @PostMapping("/users/register")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.register(signUpRequest);
    }

    @PostMapping("/admin/register_member")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<SignUpResponse> createMemberAccount(@Valid @RequestBody SignUpRequest signUprequest) {
        return userService.register(signUprequest);
    }


    @PostMapping("/register_admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SignUpResponse> createAdminAccount(@Valid @RequestBody SignUpRequest signUprequest) {
        return userService.createNewAdminAccount(signUprequest);
    }

    @PatchMapping("/edit_details")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<SignUpResponse> editDetails(@Valid @RequestBody UpdateRequest updateRequest) {
        return userService.editMemberDetails(updateRequest);
    }

    @PatchMapping("/edit_details/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<SignUpResponse> editMemberDetails(@PathVariable Long userId,
                                                            @Valid @RequestBody UpdateRequest updateRequest) {
        return userService.editMemberDetails(userId, updateRequest);
    }

    @PostMapping("/join_cycle/{contributionCycleId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Response> requestToJoinACycle(@PathVariable Long contributionCycleId,
                                                        Requests request) {
        return userService.requestToJoinACycle(request, contributionCycleId);
    }
}
