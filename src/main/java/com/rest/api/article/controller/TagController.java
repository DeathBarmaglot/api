package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}/tags")
    public Set<Tag> getAllTagsByArticleId(
            @PathVariable(value = "id") Article articleDb) {
        return tagService.getAll(articleDb);
    }

    @PostMapping("/{id}/tags")
    public Tag addNewTag(
            @PathVariable(value = "id") Article articleDb,
            @RequestBody Tag tag) {
        return tagService.addNewTag(articleDb, tag);
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    public Tag deleteTag(
            @PathVariable(value = "id") Article article,
            @PathVariable(value = "tagId") Tag tag) {
        return tagService.removeTag(article, tag);
    }
    //TODO http://localhost:8080/api/v1/posts/1/tags?id=8&id=9
    @GetMapping("/tags")
    @ResponseBody
    public Map<String, Article> getAllTaggedArticles(
            @RequestParam List<String> tag) {
      return tagService.getArticlesByTags(tag);
    }

    @GetMapping("/{id}/tags/{tagId}")
    public Tag getTagById(
            @PathVariable(value = "id") Article articleDb,
            @PathVariable(value = "tagId") Tag tag) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDb, article, "hashtags");
        tag.setArticles(Collections.singleton(article));
        return tag;
    }
}
