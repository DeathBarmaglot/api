package com.rest.api.article.service;

import com.rest.api.article.repository.CommentRepository;
import org.junit.jupiter.api.Test;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Test
    void addNewComment() {
    }

    @Test
    void getAllCommentsByPostId() {
    }
}