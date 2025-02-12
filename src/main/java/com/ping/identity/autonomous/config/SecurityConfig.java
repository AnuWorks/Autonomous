package com.ping.identity.autonomous.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/authenticate", "/myprofile", "/access-token")
                        .authenticated().anyRequest().permitAll())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/authenticate", true))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID"));

        return httpSecurity.build();
    }

}
