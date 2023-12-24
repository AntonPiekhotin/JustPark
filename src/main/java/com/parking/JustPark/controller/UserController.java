package com.parking.JustPark.controller;

import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.Role;
import com.parking.JustPark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage",
                    "User with this email already exists " + user.getEmail());
            return "registration";
        }
        userService.createUser(user);
        return "redirect:login";
    }

    @GetMapping("/hello")
    public String helloWorld(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        boolean isAdmin = user.getAuthorities().contains(Role.ADMIN);
        model.addAttribute("isAdmin", isAdmin);
        return "hello";
    }

}
