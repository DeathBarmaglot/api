package com.rest.api.article.service;

import com.rest.api.article.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    Comment getCommentById(Long id);

}
