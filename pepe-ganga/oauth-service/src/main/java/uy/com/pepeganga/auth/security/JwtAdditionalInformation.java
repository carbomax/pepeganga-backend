package uy.com.pepeganga.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.auth.services.IUserService;
import uy.com.pepeganga.auth.services.UserService;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAdditionalInformation implements TokenEnhancer {

    @Autowired
    IUserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        AuthAddInformationClaim claims = userService.findProfileByUserEmail(authentication.getName());
        Map<String, Object> additionalInformation =  new HashMap<>();
        additionalInformation.put("profileName", claims.getFirstName());
        additionalInformation.put("lastName", claims.getLastName());
        additionalInformation.put("profileId", claims.getProfileId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
