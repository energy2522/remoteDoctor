package com.remote.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String main() {
        return "feed";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin/main")
    public String successAdmin() {
        return "main_admin";
    }

    @RequestMapping("/client/feed")
    public String successClient() {
        return "feed";
    }

    @RequestMapping("/client/doctors")
    public String getDoctors() {
        return "doctors";
    }

    @RequestMapping("/doctor/main")
    public String successDoctor() {
        return "main_doctor";
    }

    @RequestMapping("/logout-success")
    public String logout() {
        return "feed";
    }
}
