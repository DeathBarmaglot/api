package com.rest.api.article.dto;

import com.rest.api.article.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class PostWithoutCommentDto {

    private Long id;

    private String title;

    private boolean star;

    private Set<String> tags;
}
