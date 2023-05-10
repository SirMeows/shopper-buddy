package com.he.engelund.service;

// Source: https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example#:~:text=package%20net.codejava%3B-,import%20org.springframework.beans.factory.annotation.Autowired%3B,%7D,-Here%2C%20we%20check

import com.he.engelund.entity.Provider;
import com.he.engelund.entity.User;
import com.he.engelund.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    //TODO: Check whether email is needed or just username will suffice
    public void processOAuthPostLogin(String username, String email) {
        User existUser = userRepository.getUserByUsernameAndEmail(username, email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            userRepository.save(newUser);
        }
    }

    public Set<User> getUsers() {
        return userRepository.findAllSet();
    }
}
