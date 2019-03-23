package com.remote.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.service.DoctorService;
import com.remote.doctor.service.SecurityService;
import com.remote.doctor.service.ClientService;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DoctorService doctorService;

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
        return "feed";
    }

    @RequestMapping("/client/feed")
    public String successClient() {
        return "feed";
    }

    @RequestMapping("/client/doctors")
    public String getDoctors() {
        return "doctors";
    }

    @RequestMapping("/client/doctors/type/{type}")
    public String getDoctorsByType(@PathVariable("type") String type, Model model) {
        System.out.println(type);
        List<DoctorDto> doctorDtos = doctorService.getDoctorsByType(type);
        double maxPrice = doctorService.getMaxPrice(doctorDtos);
        double minPrice = doctorService.getMinPrice(doctorDtos);

        model.addAttribute("doctors", doctorDtos);
        model.addAttribute("doctor.type", type);
        model.addAttribute("max_price", Math.round(maxPrice));
        model.addAttribute("min_price", Math.round(minPrice));

        return "doctors_type";
    }

    @RequestMapping("/doctor/feed")
    public String successDoctor() {
        return "feed";
    }

    @RequestMapping("/client/cabinet")
    public String goToCabinet(Model model) {
        int id = securityService.getLoggedUserId();

        ClientDto client = clientService.getClientById(id);

        model.addAttribute("client", client);

        return "client_cabinet";
    }

    @RequestMapping("/doctor/cabinet")
    public String goToDoctorCabinet(Model model) {
        int id = securityService.getLoggedUserId();

        DoctorDto doctor = doctorService.getDoctorById(id);

        model.addAttribute("doctor", doctor);
        model.addAttribute("types", doctorService.getAllTypes());
        return "doctor_cabinet";
    }

    @RequestMapping("/doctor-update")
    public String updateDoctor(DoctorDto doctorDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = doctorService.updateOldDoctor(doctorDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);
        }

        return "redirect:/doctor/cabinet";
    }

    @RequestMapping(value = "/client-update", method = RequestMethod.POST)
    public String updateClient(ClientDto clientDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = clientService.updateOldClient(clientDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);
        }

        return "redirect:/client/cabinet";
    }

    @RequestMapping("/logout-success")
    public String logout() {
        return "feed";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("types", doctorService.getAllTypes());
        return "singup";
    }

    @RequestMapping(value = "/client-singup", method = RequestMethod.POST)
    public String signUpClient(ClientDto clientDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = clientService.clientSignUp(clientDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);

            return "redirect:/signup";
        }

        securityService.autologin(clientDTO.getUsername(), clientDTO.getPassword());
        return "redirect:/client/feed";
    }

    @RequestMapping(value = "/doctor-singup", method = RequestMethod.POST)
    public String signUpDoctor(DoctorDto doctorDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        List<String> errors = doctorService.signUpDoctor(doctorDTO);

        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("errors", errors);

            return "redirect:/signup";
        }


        securityService.autologin(doctorDTO.getUsername(), doctorDTO.getPassword());
        return "redirect:/doctor/feed";
    }
}
