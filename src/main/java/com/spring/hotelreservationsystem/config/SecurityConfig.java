package com.spring.hotelreservationsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                //.password("{noop}ourpassword")
//                //.password(passwordEncoder().encode("ourpassword"))
//                .password("{bcrypt}$2a$12$HYe4zz.M8BJ5wvHMpElUs./0yjTeUgQXb1eG37kN15NENJzwsIyv")
//                //.roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
