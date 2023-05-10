package com.he.engelund.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserBuilder {

    private User user;

    public static UserBuilder create() {
        var userBuilder = new UserBuilder();
        var user = new User();
        userBuilder.setUser(user);
        return userBuilder;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public UserBuilder addId(Long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder addUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder addEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public User build() {
        var temp = user;
        user = null;
        return temp;
    }
}
