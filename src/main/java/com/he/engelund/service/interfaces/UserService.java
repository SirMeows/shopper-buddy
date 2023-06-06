package com.he.engelund.service.interfaces;

import com.he.engelund.dto.GoogleOAuth2User;
import com.he.engelund.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface UserService {
    boolean isUserRegistered(String externalId);

    void registerUser(String externalId, String externalEmail);

    void updateExternalAuthenticatedUser(GoogleOAuth2User googleOAuth2User);

    String getInternalUserIdAsStringByExternalUserId(String externalId);

    Set<User> getUsers();

    User findById(UUID userId);
}
