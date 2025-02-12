package com.softuni.Restaurant.config;

import com.softuni.Restaurant.model.enums.UserRoles;
import com.softuni.Restaurant.repository.UserRepository;
import com.softuni.Restaurant.service.UserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests

                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/","/index","/about", "/login", "/register", "/menu","/resturant1","/resturant2", "/users/login-error").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/product-add","/order-panel", "/product-panel", "/users-panel", "/recipe-add").hasRole(UserRoles.ADMIN.name())
                        .requestMatchers("/cart", "/order-add", "/profile","/profile-edit", "/shoppingCart").hasRole(UserRoles.USER.name())
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> formLogin
                        // redirect here when we access something which is not allowed.
                        // also this is the page where we perform login.
                        .loginPage("/login")
                        // The names of the input fields (login.html)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/menu")
                        .failureForwardUrl("/users/login-error")
        ).logout(
                logout -> logout
                        // the URL where we should POST something in order to perform the logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .invalidateHttpSession(true)
        ).build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        // This service translates the restaurant users and roles
        // to representation which spring security understands.
        return new UserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
