package com.remote.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.service.SecurityService;
import com.remote.doctor.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String main() {
        return "feed";
    }

    @RequestMapping("/login")
    public String login() {
        return "signin";
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

    @RequestMapping("/signup")
    public String signup() {
        return "singup";
    }

    @RequestMapping(value = "/client-singup", method = RequestMethod.POST)
    public String signUpClient(ClientDto clientDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = userService.signUpClient(clientDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);

            return "redirect:/signup";
        }

        securityService.autologin(clientDTO.getUsername(), clientDTO.getPassword());
        return "redirect:/client/feed";
    }

    @RequestMapping(value = "/doctor-singup", method = RequestMethod.POST)
    public String signUpDoctor(DoctorDto doctorDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = userService.signUpDoctor(doctorDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);

            return "redirect:/signup";
        }


        securityService.autologin(doctorDTO.getUsername(), doctorDTO.getPassword());
        return "redirect:/doctor/feed";
    }
}
