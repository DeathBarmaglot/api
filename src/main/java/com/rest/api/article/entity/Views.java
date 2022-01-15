package com.rest.api.article.entity;

public final class Views {
    public interface Id {
    }

    public interface IdTitle extends Id {
    }

    public interface WithDate extends IdTitle {
    }

    public interface FullArticle extends WithDate {
    }
}
