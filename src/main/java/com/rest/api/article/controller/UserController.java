package com.rest.api.article.controller;

import com.rest.api.article.entity.User;
import com.rest.api.article.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @PostMapping
    public User addNewUser(
            @RequestBody User user) {
        return userService.addNewUser(user);
    }
}
