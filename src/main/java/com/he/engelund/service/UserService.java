package com.he.engelund.service;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20org.springframework.beans.factory.annotation.Autowired%3B,%7D,-Here%2C%20we%20check

import com.he.engelund.entity.ExternalAuthenticatedUser;
import com.he.engelund.entity.GoogleOAuth2User;
import com.he.engelund.entity.User;
import com.he.engelund.entity.UserBuilder;
import com.he.engelund.exception.FailedToCreateNewUserException;
import com.he.engelund.repository.ExternalAuthenticatedUserRepository;
import com.he.engelund.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private ExternalAuthenticatedUserRepository externalAuthUserRepository;


    public boolean isUserRegistered(String externalId) {
        return userRepository.existsByExternalAuthenticatedUserId(externalId);
    }

    public void registerUser(String externalId, String externalEmail) {

        try {
            var newUser = UserBuilder
                    .create()
                    .addEmail(externalEmail)
                    .build();

            var newExternalAuthenticatedUser = new ExternalAuthenticatedUser();
            newExternalAuthenticatedUser.setUser(newUser);
            newExternalAuthenticatedUser.setProvidedUserId(externalId);
            newExternalAuthenticatedUser.setEmail(externalEmail);
            //TODO: Set other fields such as provider

            externalAuthUserRepository.save(newExternalAuthenticatedUser);

        } catch (Exception e) {
            throw new FailedToCreateNewUserException(e);
        }
    }

    public void updateExternalAuthenticatedUser(GoogleOAuth2User googleOAuth2User) {
        var externalAuthUser =
                userRepository.getExternalAuthenticatedUserByProvidedUserId(googleOAuth2User.getExternalUserId());

        externalAuthUser.setEmail(googleOAuth2User.getEmail());
        externalAuthUser.setImageUrl(googleOAuth2User.getImageUrl());
        externalAuthUser.setLocale(googleOAuth2User.getLocale());

        externalAuthUserRepository.save(externalAuthUser);
    }

    public String getInternalUserIdAsStringByExternalUserId(String externalId) {
        var existingUser = userRepository.getUserByExternalAuthenticatedUserId(externalId);
        return existingUser.getId().toString();
    }

    public Set<User> getUsers() {
        return userRepository.findAllSet();
    }

 /*   public ExternalAuthenticatedUser getExternalAuthenticatedUserByInternalUserId(String internalId) {
        var internalUUID = UUID.fromString(internalId);
        return userRepository.getExternalAuthenticatedUserByInternalUserId(internalUUID);
    }*/
}