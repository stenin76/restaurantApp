package com.softuni.Restaurant.controllers;

import com.softuni.Restaurant.model.UserEntity;
import com.softuni.Restaurant.model.dto.ChangeUsernameDto;
import com.softuni.Restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;


@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/profile-edit")
    public String profileEdit() {
        return "profile-edit";
    }

    @GetMapping("/showProfilePicture")
    public ResponseEntity<byte[]> showProfilePicture() {
        return userService.showProfilePicture(userService.getLoggedUserDetails().getUserName());
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
            redirectAttributes
                    .addFlashAttribute("change_user_details_message", "Your account details have been updated");

        return "redirect:/profile-edit";
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            redirectAttributes.addFlashAttribute("error_message1", "You have not selected a picture");
            return "redirect:/profile-edit";
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            redirectAttributes.addFlashAttribute("error_message2", "File size exceeds the maximum limit of 2MB");
            return "redirect:/profile-edit";
        }
        redirectAttributes.addFlashAttribute("msg", "Your profile picture has been updated");
        userService.changeUserProfilePicture(file);
        return "redirect:/profile-edit";
    }
}
