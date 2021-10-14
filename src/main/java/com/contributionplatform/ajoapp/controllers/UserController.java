package com.contributionplatform.ajoapp.controllers;

import com.contributionplatform.ajoapp.models.ContributionCycle;
import com.contributionplatform.ajoapp.models.Contributions;
import com.contributionplatform.ajoapp.models.Requests;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.ContributionCycleRequest;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.ContributionCycleResponse;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import com.contributionplatform.ajoapp.service.AdminService;
import com.contributionplatform.ajoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest userRequest){
        return userService.doLogin(userRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.register(signUpRequest);
    }


    @PatchMapping("/edit-details")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<SignUpResponse> editDetails(@Valid @RequestBody UpdateRequest updateRequest) {
        return userService.editMemberDetails(updateRequest);
    }


    @PostMapping("/join-cycle")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Response> requestToJoinACycle() {
        return userService.requestToJoinACycle();
    }


    @PostMapping("/delete-account/")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Response> requestToDeleteAccount() {
        return userService.requestToDeleteAccount();
    }

    @GetMapping("/view-cycle-members/{contributionCycleId}")
    @PreAuthorize("hasRole('MEMBER')")
    public List<User> getCycleMembers(@PathVariable Long contributionCycleId) throws Exception {
        return userService.getCycleMembers(contributionCycleId);
    }

    @GetMapping("/view-contributions/{contributionCycleId}")
    @PreAuthorize("hasRole('MEMBER')")
    public List<Contributions> getFinancialRecord(@PathVariable Long contributionCycleId) throws Exception {
        return userService.getAllContributions(contributionCycleId);
    }

    @GetMapping("/active-cycle")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MEMBER')")
    public ContributionCycle getActiveCycle() {
        return adminService.getActiveContributionCycle();
    }

    @GetMapping("/cycle/{cycleId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MEMBER')")
    public ContributionCycle getCycle(@PathVariable Long cycleId) {
        return adminService.getContributionCycleById(cycleId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MEMBER')")
    public User getUserDetails() {
        return userService.getUser();
    }

}
