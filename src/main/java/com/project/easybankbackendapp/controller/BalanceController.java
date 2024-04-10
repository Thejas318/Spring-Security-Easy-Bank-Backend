package com.project.easybankbackendapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/myBalance")
    public  String getBalanceDetails(){
        return "Here are the balance details for the user from DB";
    }

}
