package com.rest.api.article;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
