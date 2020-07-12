package com.server.dao;

import com.server.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private final Map<String, User> profiles;

    public UserDaoImpl() {
        profiles = new HashMap<>();
    }

    public void addProfile(User user) {
        profiles.put(user.getId(), user);
    }

    public User getProfile(String id) {
        return profiles.get(id);
    }

    public List<User> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }
}
