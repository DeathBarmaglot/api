package com.rest.api.article.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UsernameAndPasswordAuthRequest {

    private String username;
    private String password;
}
