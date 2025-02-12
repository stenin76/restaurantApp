package com.softuni.Restaurant.service;

import com.softuni.Restaurant.model.Role;
import com.softuni.Restaurant.model.UserEntity;
import com.softuni.Restaurant.model.dto.ChangeUsernameDto;
import com.softuni.Restaurant.model.dto.UserRegisterDto;
import com.softuni.Restaurant.model.enums.UserRoles;
import com.softuni.Restaurant.repository.RoleRepository;
import com.softuni.Restaurant.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public void changeUserProfilePicture(String path) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userToChangeProfilePicture = userRepository.findByUserName(auth.getName()).get();

        userToChangeProfilePicture.setImageProfileUrl(path);
        this.userRepository.save(userToChangeProfilePicture);
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
        return userRepository.findByUserName(auth.getName()).get();
    }
}
