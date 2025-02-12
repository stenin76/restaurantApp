package com.softuni.Restaurant.controllers;

import com.softuni.Restaurant.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class UploadController {

    private final UserService userService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\profile-images";

    public UploadController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/upload")
    @ExceptionHandler
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());

        if (Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            model.addAttribute("error1", "stop");
            return "redirect:/profile-edit";
        }

        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg",
                            "Uploaded images: " + fileNames);
        userService.changeUserProfilePicture("profile-images/" + fileNames);

        return "redirect:/profile";
    }
}
