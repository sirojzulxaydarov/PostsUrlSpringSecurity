package com.example.demo.component;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Override
    public void run(String... args) throws Exception {
        if(ddl.equals("create")) {
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            Role role3 = new Role("ROLE_OPERATOR");
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);


            User user = User.builder()
                    .email("a@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .age(18)
                    .firstName("Ahmad")
                    .lastName("Kumar")
                    .roles(List.of(role1,role2))
                    .build();
            userRepository.save(user);
        }

    }
}
