package com.softuni.Restaurant.controllers;

import com.softuni.Restaurant.model.UserEntity;
import com.softuni.Restaurant.model.dto.ChangeUsernameDto;
import com.softuni.Restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/profile-edit")
    public String profileEdit() {
        return "profile-edit";
    }

    @ModelAttribute("changeUsernameDto")
    public ChangeUsernameDto  iniChangeDto() {
        return new ChangeUsernameDto();
    }

    @ModelAttribute("loggedUserDetails")
    public String loggedUserDetails(Model model) {

        UserEntity user = userService.getLoggedUserDetails();
        model.addAttribute("loggedUserDetails", user);

        return "profile";
    }

    @PostMapping("/changeUserName")
    public String changeUserName(@Valid ChangeUsernameDto changeUsernameDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("changeUsernameDto", changeUsernameDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.changeUsernameDto", bindingResult);
            return "redirect:/profile-edit";
        }
        if (!(userService.findByUserName(changeUsernameDto.getNewUsername()) == null)) {
            bindingResult.addError(
                    new FieldError(
                            "changeUsernameDto",
                            "newUsername",
                            "Username is already taken!"));
            redirectAttributes
                    .addFlashAttribute("changeUsernameDto", changeUsernameDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.changeUsernameDto", bindingResult);
            return "redirect:/profile-edit";
        }

        this.userService.changeUserName(changeUsernameDto);
        redirectAttributes.addFlashAttribute("change_user_details_message", "Your account details have been updated.");

        return "redirect:/profile-edit";
    }
}
