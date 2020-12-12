package org.gatex.service;


import org.gatex.dao.UserDao;
import org.gatex.model.UserDTO;
import org.gatex.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
    	try {
    		return userDao.getUserById(username);
    	}catch (Exception e) {
            log.warn(e.getMessage(),e);
			throw e;
		}
    }

    public String save(UserDTO usrDto) {
        User usr=new User();
        BeanUtils.copyProperties(usrDto, usr);
        String encodedPassword=passwordEncoder.encode(usr.getPassword());
        usr.setPassword(encodedPassword);
        return userDao.save(usr);
    }

}