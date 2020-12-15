package org.gatex.dao;

import org.gatex.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {
    UserDetails getUserById(String userId);
    String save(User usr);
    boolean isAdminExists();
}
