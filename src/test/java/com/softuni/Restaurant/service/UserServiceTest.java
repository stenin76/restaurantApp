package com.softuni.Restaurant.service;

import com.softuni.Restaurant.model.Role;
import com.softuni.Restaurant.model.UserEntity;
import com.softuni.Restaurant.model.enums.UserRoles;
import com.softuni.Restaurant.repository.RoleRepository;
import com.softuni.Restaurant.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserService mockUserService;

    @Mock
    private UserEntity testUser;


    @Test
    void getAllUsers() {

        when(mockUserService.getAllUsers()).thenReturn(List.of(testUser));
        assertEquals(1,mockUserService.getAllUsers().size());
    }
}
