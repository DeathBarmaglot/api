package com.rest.api.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final CustomUserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public String registerNewUser(
            @RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('admin:write')")
    public void removeNewUser(
            @PathVariable("userId") Long id) {
        userService.removeUser(id);
    }
}
