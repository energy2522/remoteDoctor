package com.remote.doctor.controller;

import java.security.Principal;
import java.util.List;

import javax.jws.WebParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remote.doctor.domain.Chat;
import com.remote.doctor.domain.Client;
import com.remote.doctor.domain.Doctor;
import com.remote.doctor.dto.ChatDto;
import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.dto.CopyWriter;
import com.remote.doctor.dto.DiscussionDto;
import com.remote.doctor.dto.DiscussionInfoDto;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.dto.MessageDto;
import com.remote.doctor.service.ChatService;
import com.remote.doctor.service.ClientService;
import com.remote.doctor.service.DiscussionService;
import com.remote.doctor.service.DoctorService;
import com.remote.doctor.service.SecurityService;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private DiscussionService discussionService;

    @RequestMapping("/")
    public String main() {
        return "feed";
    }

    @RequestMapping("/login")
    public String login() {
        return "signin";
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
    public String getDoctorsByType(@PathVariable("type") String type, Model model, Principal principal) {
        System.out.println(type);
        int id = securityService.getLoggedUserId(principal);

        ClientDto clientDto = clientService.getClientById(id);
        List<DoctorDto> doctorDtos = doctorService.getDoctorsByType(type);
        double maxPrice = doctorService.getMaxPrice(doctorDtos);
        double minPrice = doctorService.getMinPrice(doctorDtos);

        model.addAttribute("doctors", doctorDtos);
        model.addAttribute("client", clientDto);
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
    public String goToCabinet(Principal principal, Model model) {
        int id = securityService.getLoggedUserId(principal);

        ClientDto client = clientService.getClientById(id);

        model.addAttribute("client", client);

        return "client_cabinet";
    }

    @RequestMapping("/doctor/cabinet")
    public String goToDoctorCabinet(Principal principal, Model model) {
        int id = securityService.getLoggedUserId(principal);

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

    @RequestMapping("/messages")
    public String goToChats(Principal principal, Model model) {
        int userId = securityService.getLoggedUserId(principal);
        List<ChatDto> chats = chatService.findAllByUserId(userId);

        model.addAttribute("chats", chats);
        return "chats_list";
    }

    @RequestMapping(value = "/discussion/open", method = RequestMethod.POST)
    public String openDiscussion(DiscussionDto discussion) {
        discussionService.openDiscussion(discussion);

        return "redirect:/messages";
    }

    @RequestMapping(value = "/chat/create", method = RequestMethod.POST)
    public String createChat(@RequestParam("id") int doctorId, Principal principal) {
        int clientId = securityService.getLoggedUserId(principal);

        Chat chat = chatService.getChatByClientAndDoctor(clientId, doctorId);

        if (chat == null) {
            Client client = new Client();
            Doctor doctor = new Doctor();
            client.setId(clientId);
            doctor.setId(doctorId);

            chat = chatService.createChat(client, doctor);
        }

        return "redirect:/chat/" + chat.getId();
    }


    @RequestMapping("/chat/{id}")
    public String goToChat(@PathVariable("id") int chatId, Principal principal, Model model) {
        int currentUser = securityService.getLoggedUserId(principal);
        List<MessageDto> history = chatService.getMessagesByChatId(chatId);
        CopyWriter sender = chatService.getSender(currentUser, chatId);
        CopyWriter recipient = chatService.getRecipient(currentUser, chatId);

        model.addAttribute("history", history);
        model.addAttribute("recipient", recipient);
        model.addAttribute("sender", sender);
        model.addAttribute("chatId", chatId);

        return "chat";
    }

    @RequestMapping("/discussion/{id}")
    public String goToDiscussion(@PathVariable("id") int discussionId, Model model) {
        List<MessageDto> messages = chatService.getMessagesByDiscussionId(discussionId);
        CopyWriter sender = chatService.getSender(discussionId);
        CopyWriter recipient = chatService.getRecipient(discussionId);

        model.addAttribute("history", messages);
        model.addAttribute("recipient", recipient);
        model.addAttribute("sender", sender);
        model.addAttribute("discussionId", discussionId);

        return "conversation";
    }

    @RequestMapping("/admin/main")
    public String showDiscussion(Model model) {
        List<DiscussionInfoDto> discussionInfoDtos = discussionService.getAll();

        model.addAttribute("discussions", discussionInfoDtos);

        return "admin_main";
    }

    @RequestMapping("/discussion/accept/{id}")
    public String acceptDiscussion(@PathVariable("id") int discussionId) {
        discussionService.acceptDiscussion(discussionId);

        return "redirect:/admin/main";
    }

    @RequestMapping("/discussion/deny/{id}")
    public String denyDiscussion(@PathVariable("id") int discussionId) {
        discussionService.denyDiscussion(discussionId);

        return "redirect:/admin/main";
    }

}
