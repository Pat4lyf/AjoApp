package com.contributionplatform.ajoapp.service.serviceimplementation;

import com.contributionplatform.ajoapp.enums.Roles;
import com.contributionplatform.ajoapp.exceptions.BadCredentialsException;
import com.contributionplatform.ajoapp.exceptions.ResourceNotFoundException;
import com.contributionplatform.ajoapp.exceptions.UserAlreadyExistsException;
import com.contributionplatform.ajoapp.exceptions.UserNotFoundException;
import com.contributionplatform.ajoapp.models.ContributionCycle;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.ContributionCycleRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.ContributionCycleResponse;
import com.contributionplatform.ajoapp.payloads.response.Response;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
import com.contributionplatform.ajoapp.repositories.ContributionCycleRepository;
import com.contributionplatform.ajoapp.repositories.UserRepository;
import com.contributionplatform.ajoapp.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AdminServiceImplementation implements AdminService {

    private final UserRepository userRepository;
    private final ContributionCycleRepository contributionCycleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public ResponseEntity<ContributionCycleResponse> createContributionCycle(ContributionCycleRequest contributionCycleRequest) {
        ContributionCycle contributionCycle = contributionCycleRepository.findContributionCycleByStatus(true).orElseThrow(
                () ->  new ResourceNotFoundException("CYCLE NOT FOUND", HttpStatus.NOT_FOUND));

        contributionCycle.setStatus(false);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ContributionCycle newContributionCycle = mapper.map(contributionCycleRequest, ContributionCycle.class);
        newContributionCycle.setStatus(true);

        ContributionCycleResponse contributionCycleResponse = mapper.map(newContributionCycle,
                                                                ContributionCycleResponse.class);

        return new ResponseEntity<>(contributionCycleResponse, HttpStatus.CREATED);
    }

    @Override
    public ContributionCycle getActiveContributionCycle() {
        return contributionCycleRepository.findContributionCycleByStatus(true).orElseThrow(
                () -> new ResourceNotFoundException("NO ACTIVE CYCLE", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public ContributionCycle getContributionCycleById(Long id) {
        return contributionCycleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("NO CYCLE WITH ID, " + id , HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public ResponseEntity<SignUpResponse> createNewAdminAccount(SignUpRequest signUpRequest) {
        Optional<User> admin = userRepository.findUserByEmailAddress(signUpRequest.getEmailAddress());
        if(admin.isEmpty()){
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            User newAdmin = mapper.map(signUpRequest, User.class);
            newAdmin.setRole(Roles.ADMIN.toString());
            newAdmin.setDateJoined(new Date());
            newAdmin.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            userRepository.save(newAdmin);
            SignUpResponse signUpResponse = mapper.map(newAdmin, SignUpResponse.class);
            return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
        } else throw new UserAlreadyExistsException("Admin Already Exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<SignUpResponse> editMemberDetails(Long id, UpdateRequest updateRequest) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isEmpty()) throw new UserNotFoundException("USER NOT FOUND", HttpStatus.NOT_FOUND);

        User user = optional.get();


        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(updateRequest, user);

        user.setPassword(bCryptPasswordEncoder.encode(updateRequest.getPassword()));

        userRepository.save(user);


        SignUpResponse signUpResponse = mapper.map(user, SignUpResponse.class);

        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContributionCycleResponse> editCycleDetails(UpdateRequest updateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Response> deleteCycle() {

        Response response = new Response();

        if (contributionCycleRepository.deleteByStatus(true)) {
            response.setMessage("CYCLE DELETED SUCCESSFULLY");
            response.setStatusCode(200);
        } else {
            response.setMessage("NO ACTIVE CYCLE FOUND");
            response.setStatusCode(404);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

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
}
