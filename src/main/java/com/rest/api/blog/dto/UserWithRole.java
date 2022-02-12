package com.rest.api.blog.dto;


import lombok.Data;

@Data
public class UserWithRole {
    private String username;
    private String roleName;

}