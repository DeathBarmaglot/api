package com.rest.api.article.controller;

import com.rest.api.article.entity.User;
import com.rest.api.article.service.UserService;
import com.rest.api.article.service.utils.CurrentUser;
import com.rest.api.article.service.utils.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<User> getAllUsers(@CurrentUser UserInfo username) {
        System.out.printf("username %f%n",username);
        return userService.getUsers();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public User registerNewUser(
            @RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('admin:write')")
    public void removeNewUser(
            @PathVariable("userId") User user) {
        userService.removeUser(user);
    }

//    @PutMapping("{userId}")
//    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_MANAGER')")
//    @PreAuthorize("hasAuthority('user:write')")
//    public User updateUser(
//            @PathVariable("userId") @RequestBody User user){
//        return userService.updateUser(user);
//    }
    //TODO getAllTaggedArticles Map<String, Article>
//TODO [{}] add user to [post]
//http://localhost:8080/api/v1/posts/tags +  9 & 8
}
