package com.rest.api.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Post {
    private Long id;

    private String title;

    private boolean star;

    private Set<CommentText> comments;

    private Set<Hash> tags;
}
