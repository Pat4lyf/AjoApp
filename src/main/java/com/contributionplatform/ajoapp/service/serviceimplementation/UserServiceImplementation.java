package com.contributionplatform.ajoapp.service.serviceimplementation;

import com.contributionplatform.ajoapp.configurations.security.service.UserDetailService;
import com.contributionplatform.ajoapp.configurations.security.util.JwtUtil;
import com.contributionplatform.ajoapp.enums.Roles;
import com.contributionplatform.ajoapp.exceptions.ResourceNotFoundException;
import com.contributionplatform.ajoapp.exceptions.UserAlreadyExistsException;
import com.contributionplatform.ajoapp.exceptions.BadCredentialsException;
import com.contributionplatform.ajoapp.exceptions.UserNotFoundException;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.payloads.request.LoginRequest;
import com.contributionplatform.ajoapp.payloads.request.SignUpRequest;
import com.contributionplatform.ajoapp.payloads.request.UpdateRequest;
import com.contributionplatform.ajoapp.payloads.response.LoginResponse;
import com.contributionplatform.ajoapp.payloads.response.SignUpResponse;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        Optional<User> user = userRepository.findUserByEmailAddress(emailAddress);
        if(user.isEmpty()) throw new ResourceNotFoundException(
                "Incorrect parameter; Email Address " + emailAddress + " does not exist");
        return user.get();
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
            System.out.println(bCryptPasswordEncoder.encode((signUpRequest.getPassword())));
            if(signUpRequest.getPhoneNumber() == null) throw new BadCredentialsException("Phone Number cannot be null", HttpStatus.BAD_REQUEST);
            userRepository.save(newUser);
            SignUpResponse signUpResponse = mapper.map(newUser, SignUpResponse.class);
            return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
        } else throw new UserAlreadyExistsException("Member Already Exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<SignUpResponse> createNewAdminAccount(SignUpRequest signUpRequest) {
        Optional<User> admin = userRepository.findUserByEmailAddress(signUpRequest.getEmailAddress());
        if(admin.isEmpty()){
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            User newAdmin = mapper.map(signUpRequest, User.class);
            newAdmin.setRole(Roles.ADMIN.toString());
            newAdmin.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
            userRepository.save(newAdmin);
            SignUpResponse signUpResponse = mapper.map(newAdmin, SignUpResponse.class);
            return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
        } else throw new UserAlreadyExistsException("Admin Already Exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<SignUpResponse> editMemberDetails(UpdateRequest updateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optional = userRepository.findUserByEmailAddress(email);
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


}
