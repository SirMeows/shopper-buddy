package com.he.engelund.service;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService%3B,%7D,-Here%2C%20we%20override

import com.he.engelund.entity.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
// Every time an authenticated user enters one of our pages, this google user gets loaded and checked against saved user
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        //System.out.println(oAuth2User.getAttributes());

        // use my user repo to find this OAuth2 user (reference to google user) by email or id
            // verify that external and internal user information matches (email, external id, provider name)
        // if user doesn't exist, register a new one in my repo
            // if user creation fails throw exception (show error page)
        // add my own user id to the customOAuth2 user (getAttributes)
        // optionally add different granted authorities (getAuthorities)
        return new CustomOAuth2User(oAuth2User);
    }
}
