package com.contributionplatform.ajoapp.configurations.security.service;

import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.repositories.UserRepository;
import com.contributionplatform.ajoapp.service.UserService;
import com.contributionplatform.ajoapp.service.serviceimplementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAddress(emailAddress).get();

        return UserDetail.build(user);
    }
}
