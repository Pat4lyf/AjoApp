package com.contributionplatform.ajoapp.seeder;

import com.contributionplatform.ajoapp.enums.Roles;
import com.contributionplatform.ajoapp.models.User;
import com.contributionplatform.ajoapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DataBaseSeeder {
    @Autowired
    private UserRepository userRepository;


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAdminUsersTable();
    }

    private void seedAdminUsersTable() {
        Optional<User> superAdminUser = userRepository.findUserByRole("SUPER_ADMIN");

            if(superAdminUser.isEmpty()) {
                User user = new User();
                user.setFirstName("Super");
                user.setLastName("Admin");
                user.setEmailAddress("admin@ajo.com");
                user.setPassword(new BCryptPasswordEncoder().encode("Aj1222#%^nnsergl"));
                user.setRole(Roles.SUPER_ADMIN.toString());
                userRepository.save(user);
                log.info("Super Admin Successfully Seeded");
            } else {
                log.info("Admin Seeding Not Required");
            }
    }

}
