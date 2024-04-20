package com.project.easybankbackendapp.service;

import com.project.easybankbackendapp.model.Customer;
import com.project.easybankbackendapp.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRegistrationService {

    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    public UserRegistrationService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<String> registerUser(Customer customer) {

        Customer savedCustomer = null;
        ResponseEntity response = null;

        try{
            String encodedPassword = this.passwordEncoder.encode(customer.getPwd());
            customer.setPwd(encodedPassword);
//            log.info("Customer entered password after encoding the password: {}", customer.getPwd());
            log.info("User registration has started");
            savedCustomer = customerRepository.save(customer);
            if(savedCustomer.getId() > 0){
              response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Given User has been registered successfully");
            }
        }
        catch (Exception ex){
            log.info("Exception occured while regustering the user due to: {} ", ex.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occured due to: " + ex.getMessage());
        }
        return response;
    }
}
