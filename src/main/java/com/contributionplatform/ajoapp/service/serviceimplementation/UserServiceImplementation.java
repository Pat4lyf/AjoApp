package com.contributionplatform.ajoapp.service.serviceimplementation;

import com.contributionplatform.ajoapp.configurations.security.service.UserDetailService;
import com.contributionplatform.ajoapp.configurations.security.util.JwtUtil;
import com.contributionplatform.ajoapp.enums.PaymentType;
import com.contributionplatform.ajoapp.enums.Request;
import com.contributionplatform.ajoapp.enums.RequestStatus;
import com.contributionplatform.ajoapp.enums.Roles;
import com.contributionplatform.ajoapp.exceptions.ResourceNotFoundException;
import com.contributionplatform.ajoapp.exceptions.UserAlreadyExistsException;
import com.contributionplatform.ajoapp.exceptions.BadCredentialsException;
import com.contributionplatform.ajoapp.exceptions.UserNotFoundException;
import com.contributionplatform.ajoapp.models.ContributionCycle;
import com.contributionplatform.ajoapp.models.Contributions;
import com.contributionplatform.ajoapp.models.Requests;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.LoginResponse;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import com.contributionplatform.ajoapp.paymentservice.core.Transactions;
import com.contributionplatform.ajoapp.repositories.ContributionCycleRepository;
import com.contributionplatform.ajoapp.repositories.ContributionsRepository;
import com.contributionplatform.ajoapp.repositories.RequestsRepository;
import com.contributionplatform.ajoapp.repositories.UserRepository;
import com.contributionplatform.ajoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RequestsRepository requestsRepository;
    private final ContributionsRepository contributionsRepository;
    private final ContributionCycleRepository contributionCycleRepository;
    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress).orElseThrow(
                () -> new ResourceNotFoundException("Incorrect parameter; Email Address " + emailAddress + " does not exist")
        );
    }

    @Override
    public ResponseEntity<?> doLogin(LoginRequest userRequest) {

        if(!userRepository.existsByEmailAddress(userRequest.getEmailAddress()))
            throw new UserNotFoundException("Invalid Email", HttpStatus.NOT_FOUND);

        UserDetails userDetails = userDetailService.loadUserByUsername(userRequest.getEmailAddress());

        if(!bCryptPasswordEncoder.matches(userRequest.getPassword(), userDetails.getPassword()))
            throw new BadCredentialsException("Invalid password", HttpStatus.BAD_REQUEST);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmailAddress(), userRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        LoginResponse response = new LoginResponse(jwtToken, roles);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SignUpResponse> register(SignUpRequest signUpRequest) {
        Optional<User> user = userRepository.findUserByEmailAddress(signUpRequest.getEmailAddress());
        if(user.isEmpty()){
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            User newUser = mapper.map(signUpRequest, User.class);
            newUser.setRole(Roles.MEMBER.toString());
            newUser.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            newUser.setDateJoined(new Date());
            if(signUpRequest.getPhoneNumber() == null) throw new BadCredentialsException("Phone Number cannot be null", HttpStatus.BAD_REQUEST);
            userRepository.save(newUser);
            SignUpResponse signUpResponse = mapper.map(newUser, SignUpResponse.class);
            return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
        } else throw new UserAlreadyExistsException("Member Already Exists!", HttpStatus.BAD_REQUEST);
    }


    @Override
    public User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public ResponseEntity<SignUpResponse> editMemberDetails(UpdateRequest updateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );



        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(updateRequest, user);

        user.setPassword(bCryptPasswordEncoder.encode(updateRequest.getPassword()));

        userRepository.save(user);


        SignUpResponse signUpResponse = mapper.map(user, SignUpResponse.class);

        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Response> requestToJoinACycle(Requests request, Long contributionCycleId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );

        request.setDateOfRequest(new Date());

        Optional<ContributionCycle> optionalContributionCycle = contributionCycleRepository.findById(contributionCycleId);

        ContributionCycle contributionCycle = optionalContributionCycle.get();

        int dateDifference = request.getDateOfRequest().toString().compareTo(contributionCycle.getStartDate().toString());

        Response response = new Response();
        if (dateDifference < 0) {
            request.setRequestStatus(RequestStatus.PENDING.toString());
            request.setRequestMessage(Request.JOIN_A_CYCLE.toString());

            requestsRepository.save(request);
            user.getListOfRequests().add(request);
            userRepository.save(user);


            response.setMessage("Request sent successfully");
            response.setStatusCode(200);
        } else {
            response.setMessage("You cannot join the present cycle. Wait till a new cycle begins.");
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> requestToDeleteAccount() {
        Requests request = new Requests();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );

        request.setDateOfRequest(new Date());
        request.setRequestStatus(RequestStatus.PENDING.toString());
        request.setRequestMessage(Request.DELETE_ACCOUNT.name());

        requestsRepository.save(request);
        user.getListOfRequests().add(request);
        userRepository.save(user);

        Response response = new Response();

        response.setMessage("Request sent successfully");
        response.setStatusCode(200);


//        Transactions transactions = new Transactions();
//        transactions.initializeTransaction();


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<User> getCycleMembers(Long contributionCycleId) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );

        ContributionCycle contributionCycle = contributionCycleRepository.findById(contributionCycleId).orElseThrow(
                () ->  new ResourceNotFoundException("CYCLE NOT FOUND", HttpStatus.NOT_FOUND));

        if (contributionCycle.getListOfCycleMembers().contains(user)) {
            return contributionCycle.getListOfCycleMembers();
        } else {
            throw new Exception("NOT A MEMBER OF THIS CYCLE");
        }

    }

    @Override
    public List<Contributions> getAllContributions(Long contributionCycleId) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () -> new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND)
        );

        ContributionCycle contributionCycle = contributionCycleRepository.findById(contributionCycleId).orElseThrow(
                () ->  new ResourceNotFoundException("CYCLE NOT FOUND", HttpStatus.NOT_FOUND));

        if (contributionCycle.getListOfCycleMembers().contains(user)) {
            return contributionCycle.getListOfContributions();
        } else {
            throw new Exception("NOT A MEMBER OF THIS CYCLE");
        }
    }

    @Override
    public ResponseEntity<Response> makePayment(Contributions contribution, Long contributionCycleId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmailAddress(email).orElseThrow(
                () ->  new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND));

        ContributionCycle contributionCycle = contributionCycleRepository.findById(contributionCycleId).orElseThrow(
                () ->  new ResourceNotFoundException("CYCLE NOT FOUND", HttpStatus.NOT_FOUND));


        Response response = new Response();

        if(contributionCycle.getListOfCycleMembers().contains(user)) {
            contribution.setDatePaid(new Date());

            String contributionCyclePaymentStartDate = contributionCycle.getPaymentStartDate().toString();
            String contributionCyclePaymentEndDate = contributionCycle.getPaymentEndDate().toString();

            int startDateDifference = contribution.getDatePaid().toString().compareTo(contributionCyclePaymentStartDate);
            int endDateDifference = contribution.getDatePaid().toString().compareTo(contributionCyclePaymentEndDate);


            if (startDateDifference > 0 || endDateDifference < 0) {
                contribution.setContributionCycleId(contributionCycleId);
                contribution.setAmountPaid(5000d);
                contribution.setPaymentType(PaymentType.DEBIT.name());
                contribution.setUserId(user.getUserId());
                contributionsRepository.save(contribution);
                response.setMessage("SUCCESSFUL PAYMENT");
                response.setStatusCode(200);
            } else {
                response.setStatusCode(400);
                response.setMessage("MAKE PAYMENT BETWEEN " + contributionCyclePaymentStartDate +
                        " - " + contributionCyclePaymentEndDate);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
