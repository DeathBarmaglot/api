package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    private final TagService tagService;

    @GetMapping("/{id}/tags")
    public Map<String, List<String>> getAllTagsByArticleId(
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

    @GetMapping("/tags")
    @ResponseBody
    public Map<String, List<Article>> getAll(
            @RequestParam List<String> tags) {
        return tagService.getArticlesByTags(tags);
    }

    @GetMapping("/{id}/tags/{tagId}")
    public Tag getTagById(
            @PathVariable(value = "id") Article articleDb,
            @PathVariable(value = "tagId") Tag tag) {
        return tagService.getTag(articleDb, tag);
    }
}
