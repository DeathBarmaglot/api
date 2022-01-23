package com.rest.api.article.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostWithCommentsDto {

    private Long id;

    private String title;

    private String content;

    private boolean star;

    private CommentWithoutPostDto commentWithoutPostDto;
}


