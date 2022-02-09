package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.dto.PostWithCommentsDto;
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

    public List<Tag> getAll() {
        log.info("Searching All tags");
        return tagRepository.findAll();
    }

//   Set<Article> tags = tagRepository.findTagsById(id);


    public String addNewTag(Article articleDb, Tag tag) {
        log.info("Adding New tag");
        articleDb.getTags().add(tag);
        tagRepository.save(tag);

//        Article articleDb = articleRepository.getById(article);
//        Set<Tag> setTags = new HashSet<>();
//        List<Long> ids = new ArrayList<>();
//        Set<Article> list = tagRepository.findTagsById(articleId);
//        list.forEach(l-> ids.add(l.getId()));
//        System.out.println(list);
//        tags.add(tag);
//        articleDb.setTags(tags);
//        articleRepository.save(articleDb);
        return tag.getTag();
    }

//    public Tag removeTag(Article article, Tag tag) {
//        Set<Tag> tags = article.getTags();
//        tags.remove(tag);
//        article.setTags(tags);
//        articleRepository.save(article);
//        log.info("Removing tag {}", tag.getHashtag());
//        return tag;
//    }

    public Set<String> getAllTagsById(Article article) {
        Set<Tag> setTags = article.getTags();
        Set<String> tags = new HashSet<>();
        setTags.forEach(tag -> tags.add(tag.getTag()));

//        List<Tag> tags = tagRepository.findAll();

//        List<String> hashs = new ArrayList<>();
//        tags.forEach(tag -> hashs(tag.getHashtag()));
        //Set<Tag> tags = article.getTags();

//        PostWithoutCommentsDto full = new PostWithoutCommentsDto();
//        List<CommentWithoutPostDto> commentsDto = fetch(comments);
//        BeanUtils.copyProperties(article, full);
//        full.setComments(commentsDto);
        return tags;
    }

    public Map<String, List<Article>> getArticlesByTags(List<String> tagsList) {
        Map<String, List<Article>> tags = new HashMap<>();
        if (!tagsList.isEmpty()){
            tagsList.forEach(tag -> tags.put(tag, findPostBy(tag)));
        } else {
            List<Tag> allTags = tagRepository.findAll();
            allTags.forEach(tag -> tags.put(tag.getTag(), findPostBy(tag.getTag())));
        }
        return tags;
    }


    private List<Article> findPostBy(String tag) {
        return articleRepository.findArticlesByTags(tag);
    }
//
//    public Tag getTag(Article articleDb, Tag tag) {
//        Article article = new Article();
//        BeanUtils.copyProperties(articleDb, article, "tags");
//        tag.setArticles(Collections.singleton(article));
//        return tag;
//    }
    //TODO http://localhost:8080/api/v1/posts/tags
}