package com.project.easybankbackendapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @GetMapping("/myLoan")
    public  String getLoanDetails(){
        return "Here are the loan details for the user from DB";
    }

}
