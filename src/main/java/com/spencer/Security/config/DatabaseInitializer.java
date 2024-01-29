package com.spencer.Security.config;

import com.spencer.Security.user.Role;
import com.spencer.Security.user.UserEntity;
import com.spencer.Security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByEmail("admin@gmail.com")) {
            //Create admin user
            UserEntity user = UserEntity.builder()
                    .firstname("admin")
                    .lastname("Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(user);

        }
    }
}
