package com.softuni.Restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String onFailure(Model model) {

        model.addAttribute("login_error_message", "true");

        return "login";
    }
}
