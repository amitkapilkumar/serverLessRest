package com.server.builder;

import com.server.model.Tier;
import com.server.model.User;

public class UserBuilder {
    private String id;
    private String name;
    private String email;
    private Tier tier;

    private UserBuilder() {}

    public static UserBuilder aUserBuilder() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withTier(Tier tier) {
        this.tier = tier;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setTier(tier);
        return user;
    }
}
