package com.example.demo.component;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Override
    public void run(String... args) throws Exception {
        if(ddl.equals("create")) {
            User user = User.builder()
                    .email("a@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .age(18)
                    .firstName("Ahmad")
                    .lastName("Kumar")
                    .build();
            userRepository.save(user);
        }

    }
}
