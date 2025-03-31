package com.softuni.Restaurant.service;

import com.softuni.Restaurant.model.Role;
import com.softuni.Restaurant.model.UserEntity;
import com.softuni.Restaurant.model.dto.ChangeUsernameDto;
import com.softuni.Restaurant.model.dto.UserRegisterDto;
import com.softuni.Restaurant.model.enums.UserRoles;
import com.softuni.Restaurant.repository.RoleRepository;
import com.softuni.Restaurant.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final UserDetailsService userDetailsService;


    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }

    public void registerUser(UserRegisterDto userRegisterDto) {

        UserEntity userEntityToAdd = new UserEntity();
        Role roleToAdd = this.roleRepository.findConditionByUserRoles(UserRoles.USER);

        userEntityToAdd.setUserName(userRegisterDto.getUsername());
        userEntityToAdd.setEmail(userRegisterDto.getEmail());
        userEntityToAdd.setPassword(encoder.encode(userRegisterDto.getPassword()));
        userEntityToAdd.getRoles().add(roleToAdd);

        this.userRepository.save(userEntityToAdd);
    }

    public void changeUserName(ChangeUsernameDto changeUsernameDto) {

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userToChangeName = userRepository.findByUserName(currentAuthentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        // Update and save user with new username
        userToChangeName.setUserName(changeUsernameDto.getNewUsername());
        userRepository.save(userToChangeName);

        // Create a new Authentication object with the new username
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                userDetailsService.loadUserByUsername(changeUsernameDto.getNewUsername()), // new user
                currentAuthentication.getCredentials(),// keep the same credentials
                currentAuthentication.getAuthorities() // keep the same authorities
        );

        // Update the security context with the new Authentication object
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    public void changeUserProfilePicture(MultipartFile file) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userToChangeProfilePicture = userRepository.findByUserName(auth.getName()).orElse(null);

        assert userToChangeProfilePicture != null;
        userToChangeProfilePicture.setProfilePicture (file.getBytes());
        this.userRepository.save(userToChangeProfilePicture);
    }

    public ResponseEntity<byte[]> showProfilePicture(String name) {

        Optional<UserEntity> user = userRepository.findByUserName(name);

        byte[] image = user.orElseThrow().getProfilePicture(); // Retrieve image as byte array from database

        if (image != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Or the appropriate content type
                    .body(image);
        } else {
            // If image is null or not found, serve the default image from the folder
            Path path = Paths.get("src/main/resources/static/images/profile.jpg");
            try {
                byte[] defaultImage = Files.readAllBytes(path);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Or the appropriate content type
                        .body(defaultImage);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    public void addUserRole (Long id) {

        UserEntity userToAddRole = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userToAddRole.getRoles().add(roleRepository.findConditionByUserRoles(UserRoles.ADMIN));
        this.userRepository.save(userToAddRole);
    }

    public UserEntity findByUserName(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<UserEntity> getAllUsers () {
        return userRepository.findAll();
    }

    public UserEntity getLoggedUserDetails() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserName(auth.getName()).orElse(null);
    }
}
