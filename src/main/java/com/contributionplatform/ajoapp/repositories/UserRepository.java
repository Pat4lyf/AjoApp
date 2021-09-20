package com.contributionplatform.ajoapp.repositories;

import com.contributionplatform.ajoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailAddress(String emailAddress);
    Boolean existsByEmailAddressAndPassword(String emailAddress, String password);
    Optional<User> findUserByRole(String role);
    Optional<User> findUserByEmailAddress(String emailAddress);
}
