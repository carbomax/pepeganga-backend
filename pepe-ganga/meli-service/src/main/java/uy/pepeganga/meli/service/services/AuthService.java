package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import meli_marketplace_lib.OAuth20Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.pepeganga.meli.service.repository.MeliAccountRepository;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;

@Service
public class AuthService implements IAuthService {

    @Autowired
    OAuth20Api auth20Api;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MeliAccountRepository meliAccountRepository;

    @Override
    public MeliAutheticationResponse getToken(Integer profileId, String code) throws ApiException {
      //  String grantType = "authorization_code"; // or 'refresh_token' if you need get one new token
        String grantType = "authorization_code"; // or 'refresh_token' if you need get one new token
        String clientId = "5381382874135569"; // Your client_id
        String clientSecret = "ach1Wz5swsZTrQVJ9UTdKcAkg1Rsc0UA"; // Your client_secret
        String redirectUri = "https://localhost:4200/home/meli-accounts"; // Your redirect_uri
        String refreshToken = ""; // Your refresh_token

            MeliAutheticationResponse meliAutheticationResponse = mapper.convertValue(auth20Api.getToken(grantType, clientId, clientSecret, redirectUri, code, refreshToken), MeliAutheticationResponse.class);
            return meliAutheticationResponse;

    }

    @Override
    public MeliAccount createAccountByProfile(Integer profileId, MeliAccount meliAccount) {
        Profile profile =  new Profile();
        profile.setId(profileId);
        meliAccount.setProfile(profile);
        return meliAccountRepository.save(meliAccount);
    }


}
