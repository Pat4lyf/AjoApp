//package com.contributionplatform.ajoapp.paymentservice.controller;
//
//import com.contributionplatform.ajoapp.paymentservice.InitializeTransactionRequestDTO;
//import com.contributionplatform.ajoapp.paymentservice.InitializeTransactionResponseDTO;
//import com.contributionplatform.ajoapp.paymentservice.service.InitializeTransactionService;
//import com.contributionplatform.ajoapp.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@AllArgsConstructor
//@RestController
//public class InitializeTransactionController {
//
//    private final InitializeTransactionService initializeTransactionService;
//    private final UserService userService;
//
//    @PostMapping("/initialize_transaction/")
//    @PreAuthorize("hasRole('MEMBER')")
//    public InitializeTransactionResponseDTO initializeTransaction(
//            @RequestBody InitializeTransactionRequestDTO initializeTransactionRequestDTO) {
//        return initializeTransactionService.initializeTransaction(initializeTransactionRequestDTO);
//    }
//
//
//    @GetMapping("/verify_transaction/")
//    @PreAuthorize("hasRole('MEMBER')")
//    public InitializeTransactionResponseDTO verifyTransaction(
//            @RequestBody InitializeTransactionRequestDTO initializeTransactionRequestDTO) {
//        return initializeTransactionService.initializeTransaction(initializeTransactionRequestDTO);
//    }
//}
