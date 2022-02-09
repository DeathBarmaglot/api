package com.rest.api.article.service.utils;

import com.rest.api.article.entity.Article;

import java.util.*;

public class DtoMapper {

    protected Map<String, List<String>> tagsMapper(Article article) {
        List<String> tags = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
//        Set<Tag> tags = article.getTags();
//        tags.forEach(tag -> tags.add(tag.getHashtag()));
        map.put("tags", tags);
        return map;
    }
}