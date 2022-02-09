package com.rest.api;

import com.rest.api.article.entity.Role;
import com.rest.api.article.entity.User;
import com.rest.api.article.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;

import static com.rest.api.article.security.ApplicationUserPermission.*;
import static com.rest.api.article.security.ApplicationUserPermission.ROLE_WRITER;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_WRITER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new User(null, "John Travolta", "john", "1", new HashSet<>(), new HashSet(), new ArrayList<>()));
//            userService.saveUser(new User(null, "Will Smith", "will", "1", new HashSet<>(), new HashSet(), new ArrayList<>()));
//            userService.saveUser(new User(null, "Jim Carry", "jim", "1", new HashSet<>(), new HashSet(), new ArrayList<>()));;
//            userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "1", new HashSet<>(), new HashSet(), new ArrayList<>()));
//
//            userService.addRoleToUser("john", "ROLE_USER");
//            userService.addRoleToUser("will", "ROLE_WRITER");
//            userService.addRoleToUser("jim", "ROLE_ADMIN");
//            userService.addRoleToUser("joe", "ROLE_ADMIN");
//            userService.addRoleToUser("joe", "ROLE_USER");
//            userService.addRoleToUser("joe", "ROLE_WRITER");
//        };
//    }
}

