//package com.rest.api.article.controller;
//
//import com.rest.api.article.entity.User;
//import com.rest.api.article.service.UserService;
//import com.rest.api.article.service.utils.CurrentUser;
//import com.rest.api.article.dto.UserInfo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Transactional
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/admin")
//public class AdminController {
//
//    private final UserService userService;
//
////    @GetMapping
////    @PreAuthorize("hasAuthority('admin:read')")
////    public List<User> getAllUsers(@CurrentUser UserInfo username) {
////        System.out.printf("username %f%n", username);
////        return userService.getUsers();
////    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('admin:write')")
//    public String registerNewUser(
//            @RequestBody User user) {
//        return userService.saveUser(user);
//    }
//
//    @DeleteMapping("{userId}")
//    @PreAuthorize("hasAuthority('admin:write')")
//    public void removeNewUser(
//            @PathVariable("userId") Long id) {
//        userService.removeUser(id);
//    }
//}
