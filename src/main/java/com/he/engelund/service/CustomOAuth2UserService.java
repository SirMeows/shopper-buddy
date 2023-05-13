package com.he.engelund.service;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService%3B,%7D,-Here%2C%20we%20override

import com.he.engelund.entity.GoogleOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

    // Every time an authenticated user enters one of our pages it gets loaded and checked against saved user
    // Spring needs the modified OAuth2User that it gets through the loadUser method
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User =  super.loadUser(userRequest);
        var googleOAuth2User = new GoogleOAuth2User(oAuth2User);
        var googleOAuth2UserId = googleOAuth2User.getExternalUserId();
        var isRegistered= userService.isUserRegistered(googleOAuth2UserId);

        //TODO: Add check for whether user with this email has already been registered
        if(!isRegistered) {
           userService.registerUser(googleOAuth2UserId, googleOAuth2User.getEmail());
        } else {
            userService.updateExternalAuthenticatedUser(googleOAuth2User);
        }

        // Adds internalUserId to attribute map
        var internalUserId = userService.getInternalUserIdAsStringByExternalUserId(googleOAuth2UserId);
        googleOAuth2User.addInternalUserId(internalUserId);

        // optionally add different granted authorities (getAuthorities)

        return googleOAuth2User;
    }
}