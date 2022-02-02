package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.utils.DtoMapper;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService extends DtoMapper {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;

    public Map<String, List<String>> getAll(Article article) {
        log.info("Searching All tags");
        return tagsMapper(article);
    }

    public Tag addNewTag(Article articleDb, Tag tag) {
        Set<Tag> tags = articleDb.getHashtags();
        tags.add(tag);
        articleDb.setHashtags(tags);
        log.info("Adding New tag");
        tagRepository.save(tag);
        return tag;
    }

    public Tag removeTag(Article article, Tag tag) {
        Set<Tag> tags = article.getHashtags();
        tags.remove(tag);
        article.setHashtags(tags);
        articleRepository.save(article);
        log.info("Removing tag {}", tag.getHashtag());
        return tag;
    }

    public Map<String, List<Article>> getArticlesByTags(List<String> tagsList) {
        Map<String, List<Article>> tags = new HashMap<>();
        if (!tagsList.isEmpty()){
            tagsList.forEach(tag -> tags.put(tag, findPostBy(tag)));
        } else {
            List<Tag> allTags = tagRepository.findAll();
            allTags.forEach(tag -> tags.put(tag.getHashtag(), findPostBy(tag.getHashtag())));
        }
        return tags;
    }

    public  List<Tag> getAllTags() {
        List<Tag> allTags = tagRepository.findAll();
        return allTags;
    }

    private List<Article> findPostBy(String tag) {
        return articleRepository.findByHashtags_hashtag(tag);
    }

    public Tag getTag(Article articleDb, Tag tag) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDb, article, "hashtags");
        tag.setArticles(Collections.singleton(article));
        return tag;
    }
}