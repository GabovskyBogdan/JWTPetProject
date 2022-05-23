package com.insideinterview.userservice;

import com.insideinterview.userservice.domain.AppUser;
import com.insideinterview.userservice.domain.Message;
import com.insideinterview.userservice.domain.Role;
import com.insideinterview.userservice.service.MessageService;
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
    CommandLineRunner run(UserService userService, MessageService messageService) {
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

            messageService.saveMessage(new Message(null, "john", "blablabla1"));
            messageService.saveMessage(new Message(null, "john", "blablabla2"));
            messageService.saveMessage(new Message(null, "john", "blablabla3"));
            messageService.saveMessage(new Message(null, "john", "blablabla4"));
            messageService.saveMessage(new Message(null, "john", "blablabla5"));
            messageService.saveMessage(new Message(null, "john", "blablabla6"));
            messageService.saveMessage(new Message(null, "john", "blablabla7"));
            messageService.saveMessage(new Message(null, "john", "blablabla8"));
            messageService.saveMessage(new Message(null, "john", "blablabla9"));
        };
    }

}
