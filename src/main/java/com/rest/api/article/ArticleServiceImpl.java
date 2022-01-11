package com.rest.api.article;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements  ArticleService{

    @Autowired
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
    public Article getById(long id) {
        return articleRepository.getById(id);
    }

    @Override
    public void removeArticle(long id) { articleRepository.deleteById(id);
    }
}
