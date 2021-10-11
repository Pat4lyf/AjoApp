package com.contributionplatform.ajoapp.controllers;

import com.contributionplatform.ajoapp.payloads.request.ContributionCycleRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.ContributionCycleResponse;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import com.contributionplatform.ajoapp.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/create-cycle")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<ContributionCycleResponse> createContributionCycle(@Valid @RequestBody ContributionCycleRequest contributionCycleRequest) {
        return adminService.createContributionCycle(contributionCycleRequest);
    }

    @PatchMapping("/edit-details/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<SignUpResponse> editMemberDetails(@PathVariable Long userId,
                                                            @Valid @RequestBody UpdateRequest updateRequest) {
        return adminService.editMemberDetails(userId, updateRequest);
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SignUpResponse> createAdminAccount(@Valid @RequestBody SignUpRequest signUprequest) {
        return adminService.createNewAdminAccount(signUprequest);
    }

    @PostMapping("/admin/register-member")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<SignUpResponse> createMemberAccount(@Valid @RequestBody SignUpRequest signUprequest) {
        return adminService.register(signUprequest);
    }

    @DeleteMapping("/delete-cycle")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Response> deleteContributionCycle() {
        return adminService.deleteCycle();
    }

}
