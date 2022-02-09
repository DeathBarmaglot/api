package com.rest.api.article.dto;

import com.rest.api.article.entity.Comment;
import com.rest.api.article.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class PostWithCommentsDto {

    private Long id;

    private String title;

    private String content;

    private boolean star;

    private List<CommentWithoutPostDto> comments;

    private Set<String> tags;
}


