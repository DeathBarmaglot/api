package com.rest.api.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentWithoutPostDto {

    private Long id;

    private String text;

    private LocalDateTime createdAt;
}
