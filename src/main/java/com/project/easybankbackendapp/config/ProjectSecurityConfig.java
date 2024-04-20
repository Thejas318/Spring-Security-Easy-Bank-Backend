package com.project.easybankbackendapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;
import java.util.Random;
import java.util.random.RandomGenerator;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class ProjectSecurityConfig {

    /**
     * If we create a bean of SecurityFilterChain like below thr default rules for authorization will be overridden
     * Here we are defining custom rules for authenticating few endpoints and permitting all the requests to few
    */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("I am the one who is securing the requets now");
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoan").authenticated());
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) ->
                requests.requestMatchers("/contact", "/notices", "/registeruser").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
