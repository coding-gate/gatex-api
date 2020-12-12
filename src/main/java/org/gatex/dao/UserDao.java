package org.gatex.dao;

import org.gatex.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {
   public UserDetails getUserById(String userId);
   public String save(User usr);
}
