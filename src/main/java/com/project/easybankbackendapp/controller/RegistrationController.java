package com.project.easybankbackendapp.controller;

import com.project.easybankbackendapp.model.Customer;
import com.project.easybankbackendapp.repository.CustomerRepository;
import com.project.easybankbackendapp.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RegistrationController {

    private UserRegistrationService userRegistrationService;

    private CustomerRepository customerRepository;

    public RegistrationController(UserRegistrationService userRegistrationService, CustomerRepository customerRepository) {
        this.userRegistrationService = userRegistrationService;
        this.customerRepository = customerRepository;
    }


    @PostMapping("/registeruser")
    private ResponseEntity<String> registerCustomer(@RequestBody Customer customer,
                                            HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse){

        ResponseEntity entity = this.userRegistrationService.registerUser(customer);

        return entity;
    }

    @GetMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        log.info("Request reached /user API after authentication");
        List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            log.info("The customer information being passed to FE: {}", customers.get(0).toString());
            return customers.get(0);
        } else {
            return null;
        }

    }
}
