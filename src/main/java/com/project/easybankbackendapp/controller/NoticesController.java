package com.project.easybankbackendapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

    @GetMapping("/notices")
    public  String getNoticeDetails(){
        return "Here are the notice details for the user from DB";
    }

}
