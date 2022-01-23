package com.rest.api.article.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostWithoutCommentDto {

    private Long id;

    private String title;

    private String content;

    private boolean star;
}
