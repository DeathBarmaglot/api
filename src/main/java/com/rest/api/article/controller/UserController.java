package com.rest.api.article.controller;

import com.rest.api.article.entity.User;
import com.rest.api.article.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getUsers();
    }

    @PostMapping
    public User addNewUser(
            @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/{userId}/role")
    public void addNewRole(
            @PathVariable(value = "userId") String username,
            @RequestBody String role) {
        userService.allRoleToUser(username, role);
    }
}
