package com.insideinterview.userservice;

import com.insideinterview.userservice.domain.AppUser;
import com.insideinterview.userservice.domain.Role;
import com.insideinterview.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "john", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "will", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "jim", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "arni", "1234", new ArrayList<>()));

            userService.addRoleToUser("john", "ROLE_USER");
            userService.addRoleToUser("john", "ROLE_MANAGER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("arni", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arni", "ROLE_ADMIN");
            userService.addRoleToUser("arni", "ROLE_USER");
        };
    }

}
