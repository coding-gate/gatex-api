package org.gatex.dao.impl;

import org.gatex.dao.UserDao;
import org.gatex.entity.User;
import org.gatex.model.AppUserPrincipal;
import org.gatex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoUserDao implements UserDao {

	private UserRepository userRepository;
	private MongoTemplate mongoTemplate;

	public MongoUserDao(UserRepository userRepository, MongoTemplate mongoTemplate) {
		this.userRepository = userRepository;
		this.mongoTemplate=mongoTemplate;
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

	@Override
	public boolean isAdminExists() {
		Query query= new Query(Criteria.where("roles").all("ADMIN"));
		List<User> users=mongoTemplate.find(query, User.class);
		return !(users.isEmpty());
	}

}
