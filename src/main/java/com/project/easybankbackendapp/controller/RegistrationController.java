package com.project.easybankbackendapp.controller;

import com.project.easybankbackendapp.model.Customer;
import com.project.easybankbackendapp.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private UserRegistrationService userRegistrationService;


    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }


    @PostMapping("/registeruser")
    private ResponseEntity<String> registerCustomer(@RequestBody Customer customer,
                                            HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse){

        ResponseEntity entity = this.userRegistrationService.registerUser(customer);

        return entity;


    }
}
