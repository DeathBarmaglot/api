package com.rest.api.article;

import java.util.List;

public interface ArticleService {

    void saveArticle(Article article);

    Article getArticle(String title);

    List<Article> getArticles();

    Article getById(long id);

    void removeArticle(long id);
}
