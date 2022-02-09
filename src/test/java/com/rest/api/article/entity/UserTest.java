package com.rest.api.article.entity;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Set;

@DatabaseSetup("/data.xml")
class UserTest extends BaseTest{

//    @Test
//    public void lazyCollection() {
//        User user = session.get(User.class, 100);
//        Set<Article> article = user.getArticles();
//        article.size();
//    }

//    @Test
//    public void lazyEntity() {
//        Article article = session.get(Article.class, 10);
//        List<Comment> comments = article.getComments();
//        comments.get(0).getComment_id();
//    }

//    @Test
//    public void loadMethod() {
//        User user = session.load(User.class, 100);
//        user.getEmail();
//    }
//
//    @Test
//    @Commit
//    public void dirtyChecking() {
//        Article article = session.get(Article.class, 10);
//        article.setContent("NEW");
//
//        User user = session.load(User.class, 100);
//    }
}