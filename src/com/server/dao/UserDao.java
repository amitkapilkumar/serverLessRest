package com.server.dao;

import com.server.model.User;
import java.util.List;

public interface UserDao {
    void addProfile(User user);
    User getProfile(String id);
    List<User> getAllProfiles();
}
