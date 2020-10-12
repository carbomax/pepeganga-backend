package uy.com.pepeganga.auth.services;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.auth.clients.UserFeignClient;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  UserFeignClient client;

    @Override
    public UserDetails loadUserByUsername(String email)  {
       uy.com.pepeganga.business.common.entities.User user = findByEmail(email);

       try {

           List<GrantedAuthority> authorities = user.getRoles().stream()
                   .map( role -> new SimpleGrantedAuthority(role.getName()))
                   .collect(Collectors.toList());

           return new User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
       }catch (FeignException e){
           logger.info("Login error, user with email: {} ,not fount", user.getEmail());
           throw new UsernameNotFoundException(String.format("Login error, user with email: %s ,not fount", user.getEmail()));
       }


    }
    
    @Override
	public uy.com.pepeganga.business.common.entities.User findByEmail(String email) {
		return client.findUserByEmail(email);
	}

    @Override
    public AuthAddInformationClaim findProfileByUserEmail(String email) {
        return client.findProfileByUserEmail(email);
    }

    @Override
    public uy.com.pepeganga.business.common.entities.User updateUser(uy.com.pepeganga.business.common.entities.User user, Integer id) {
        return client.updateUser(user, id);
    }

}
