package com.rest.api.article.entity;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@SpringBootTest
@ContextConfiguration
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
public abstract class BaseTest {

    @PersistenceContext
    protected EntityManager em;

    protected Session session;

    @Before("")
    public void dbAllSet() {
        session = em.unwrap(Session.class);
    }
}