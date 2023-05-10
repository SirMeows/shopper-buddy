package com.he.engelund.entity;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20java.util.Collection%3B,%7D,-Here%2C%20this%20class

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override // add my user id to this map
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override// add SUPER_ADMIN or other authorities to collection
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("username");
    }


    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }
}