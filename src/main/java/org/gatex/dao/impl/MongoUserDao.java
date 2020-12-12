package org.gatex.dao.impl;

import org.gatex.dao.UserDao;
import org.gatex.entity.User;
import org.gatex.model.AppUserPrincipal;
import org.gatex.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MongoUserDao implements UserDao {

	private UserRepository userRepository;

	public MongoUserDao(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails getUserById(String userId) {		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + userId + "not found"));
		return new AppUserPrincipal(user);
	}

	@Override
	public String save(User usr) {
		User savedUser=userRepository.insert(usr);
		return savedUser.getId();
	}
	
}
