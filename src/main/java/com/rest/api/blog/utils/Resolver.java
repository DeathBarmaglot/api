package com.rest.api.blog.utils;

import com.rest.api.blog.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class Resolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) &&
                parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String key = "secretKey";
        String token = httpServletRequest.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).build().parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        return new UserInfo(username);
    }
}
