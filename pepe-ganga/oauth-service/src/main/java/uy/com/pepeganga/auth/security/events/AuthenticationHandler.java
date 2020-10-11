package uy.com.pepeganga.auth.security.events;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.auth.services.IUserService;
import uy.com.pepeganga.business.common.entities.User;

@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

    private Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Autowired
    IUserService userService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails.getUsername().equals("pepeganga_app"))
            return;

        logger.info("Login success for user with email: {}", userDetails.getUsername());


        User user = userService.findByEmail(userDetails.getUsername());
        if(user.getLoginAttempts() > 0 && Boolean.TRUE.equals(user.getEnabled())){
            user.setLoginAttempts(0);
            userService.updateUser(user, user.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        logger.error("Authentication error: {}", exception.getMessage());

        try{
            User user = userService.findByEmail(authentication.getName());
            if(user.getLoginAttempts() >= 3){
                logger.error("El user with email {} will be disabled for three bad login attempts: {}", user.getEmail(), user.getLoginAttempts());
                user.setEnabled(false);
            }
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            userService.updateUser(user, user.getId());
        } catch (FeignException e){
            logger.error("User not found with email {}", authentication.getName());
        }
    }
}
