package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] allowedPaths = {
                                    "/web/ukr-lit-exchange",
                                    "/web/ukr-lit-exchange/welcome",
        };

        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/registration", "/login").not().fullyAuthenticated()
                                .requestMatchers(
                                        "/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers(allowedPaths).permitAll()
                                .anyRequest().authenticated())
                        .formLogin(login -> login
                                .loginPage("/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl("/web/ukr-lit-exchange/groups", true)
                                .failureUrl("/login?error=true?"))
                            .logout(logout -> logout
                                    .logoutUrl("/perform_logout")
                                    .logoutSuccessUrl("/login")
                                    .deleteCookies("JSESSIONID"))
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
