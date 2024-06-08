package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(m -> {
            m.
                    requestMatchers("/", "/css/**", "/auth/**","/file/**","/error").permitAll()
                    .requestMatchers("/posts").hasRole("USER")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated();
        });
        http.userDetailsService(customUserDetailsService);
        http.formLogin(m -> {
            m.loginPage("/auth/login")
                    .defaultSuccessUrl("/")
                    .failureHandler((request, response, e) -> {
                        response.sendRedirect("/auth/login?error=true");
                    });

        });
        http.logout(m -> {
            m.logoutUrl("/auth/logout")
                    .logoutRequestMatcher(
                            new AntPathRequestMatcher(
                                    "/auth/logout", "POST"));
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
