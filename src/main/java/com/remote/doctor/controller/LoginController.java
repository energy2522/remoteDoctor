package com.remote.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin/main")
    public String successAdmin() {
        return "main_admin";
    }

    @RequestMapping("/client/main")
    public String successClient() {
        return "main_client";
    }

    @RequestMapping("/doctor/main")
    public String successDoctor() {
        return "main_doctor";
    }
}
