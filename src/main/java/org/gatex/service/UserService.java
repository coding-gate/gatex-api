package org.gatex.service;


import org.gatex.dao.UserDao;
import org.gatex.exception.InvalidKeyException;
import org.gatex.model.UserDTO;
import org.gatex.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private String APP_KEY;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder,
                       @Value("${admin.account.key}") String APP_KEY) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.APP_KEY=APP_KEY;
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

    public String save(UserDTO usrDto)  {
        User usr=new User();
        BeanUtils.copyProperties(usrDto, usr);
        String encodedPassword=passwordEncoder.encode(usr.getPassword());
        usr.setPassword(encodedPassword);

        if(usrDto.getType().equalsIgnoreCase("admin")){
            if(APP_KEY.equalsIgnoreCase(usrDto.getKey())){
                usr.setRoles(new String[]{"ADMIN"});
            }else{
                throw new InvalidKeyException("Invalid Key");
            }
        }else{
            usr.setRoles(new String[]{"USER"});
        }

        return userDao.save(usr);
    }

    public String isAdminExists(){
       return  userDao.isAdminExists() ? "YES" : "NO";
    }

}