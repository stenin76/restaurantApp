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
import java.nio.file.Path;
import java.nio.file.Paths;
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
        redirectAttributes.addFlashAttribute("change_user_details_message", "Your account details have been updated.");

        return "redirect:/profile-edit";
    }

    @PostMapping("/upload")
    @ExceptionHandler
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(Objects.requireNonNull(file.getOriginalFilename()));
        fileNames.append(file.getOriginalFilename());

        if (Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            model.addAttribute("error1", "stop");
            return "redirect:/profile-edit";
        }

        model.addAttribute("msg", "Uploaded images: " + fileNames);
        userService.changeUserProfilePicture(file);
        return "redirect:/profile";
    }
}
