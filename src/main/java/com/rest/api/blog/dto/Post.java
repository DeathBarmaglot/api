package com.rest.api.blog.dto;

import com.rest.api.blog.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class Post {
    private Long id;

    private String title;

    private String content;

    private boolean star;

    private List<CommentWithoutPostDto> comments;

    private Set<String> tags;

    private User user;
}
