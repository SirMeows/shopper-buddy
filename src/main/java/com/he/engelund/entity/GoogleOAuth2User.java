package com.he.engelund.entity;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20java.util.Collection%3B,%7D,-Here%2C%20this%20class

import java.util.Collection;
import java.util.Map;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Setter
public class GoogleOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    public Map<String, Object> oauth2UserAttributes = oauth2User.getAttributes();

    public GoogleOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2UserAttributes;
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

    public String getExternalUserId() { return oauth2User.getAttribute("sub"); }

    public String getFirstName() {
        return oauth2User.getAttribute("given_name");
    }

    public String getSurname() {
        return oauth2User.getAttribute("family_name");
    }

    public String getImageUrl() {
        return oauth2User.getAttribute("picture");
    }

    public boolean isVerifiedEmail() {
        return oauth2User.getAttribute("email_verified");
    }

    public String getLocale() {
        return oauth2User.getAttribute("locale");
    }

    public void addInternalUserId(String internalUserId) {
        oauth2UserAttributes.put("internal_user_id", internalUserId);
    }

    public String getInternalUserId() {
        return oauth2User.getAttribute("internal_user_id");
    }
}