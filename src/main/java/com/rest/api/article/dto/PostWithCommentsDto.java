package com.rest.api.article.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostWithCommentsDto {

    private Long id;

    private String title;

    private String content;

    private boolean star;

    private List<CommentWithoutPostDto> comments;

    private List<String> tags;
}


