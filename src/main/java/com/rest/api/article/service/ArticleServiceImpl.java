package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    @Override
    public void saveArticle(Article article) {articleRepository.save(article);}

    @Override
    public Article getArticle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @Override
    public void removeArticle(Long id) { articleRepository.deleteById(id);
    }
}
