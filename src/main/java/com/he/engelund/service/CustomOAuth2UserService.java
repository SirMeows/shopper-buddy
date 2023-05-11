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
// Every time an authenticated user enters one of our pages, this google user gets loaded and checked against saved user
    private UserService userService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        GoogleOAuth2User googleOAuth2User = new GoogleOAuth2User(oAuth2User);
        var googleOAuth2UserId = googleOAuth2User.getExternalUserId();

       var isRegistered= userService.isUserRegistered(googleOAuth2UserId);

       if(isRegistered) {
           // update externalAuthenticatedUser
       } else {
           userService.registerUser(googleOAuth2UserId, googleOAuth2User.getEmail());
       }


        // if ExternalAuthenticatedUser and CustomOAuth2User info doesn't match, then update

        // if user creation fails throw exception (show error page)

        // add my own user id to the customOAuth2 user (getAttributes)

        // optionally add different granted authorities (getAuthorities)


        return oAuth2User; // add internal user id here
    }
}
