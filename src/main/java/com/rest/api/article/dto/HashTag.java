package com.rest.api.article.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class HashTag {

    @Setter
    @Getter
    private Set<String> tag;
}
