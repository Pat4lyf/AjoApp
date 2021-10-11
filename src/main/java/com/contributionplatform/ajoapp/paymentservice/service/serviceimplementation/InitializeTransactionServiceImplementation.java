//package com.contributionplatform.ajoapp.paymentservice.service.serviceimplementation;
//
//import com.contributionplatform.ajoapp.paymentservice.InitializeTransactionRequestDTO;
//import com.contributionplatform.ajoapp.paymentservice.InitializeTransactionResponseDTO;
//import com.contributionplatform.ajoapp.paymentservice.service.InitializeTransactionService;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class InitializeTransactionServiceImplementation implements InitializeTransactionService {
//
//    @Override
//    public InitializeTransactionResponseDTO initializeTransaction(
//            InitializeTransactionRequestDTO initializeTransactionRequestDTO) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://api.paystack.co/transaction/initialize";
//
//            HttpHeaders headers = new HttpHeaders();
//
//            String key = "sk_test_42da6dc739d3dc54596e4bc6a41f4cb163078299";
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + key);
//
//            HttpEntity<InitializeTransactionRequestDTO>
//                    entity = new HttpEntity<InitializeTransactionRequestDTO>(initializeTransactionRequestDTO, headers);
//
//            ResponseEntity<InitializeTransactionResponseDTO>
//                    response = restTemplate.postForEntity(url, entity, InitializeTransactionResponseDTO.class);
//            return response.getBody();
//        }
//}
