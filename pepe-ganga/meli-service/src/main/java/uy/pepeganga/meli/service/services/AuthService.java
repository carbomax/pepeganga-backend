package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import meli_marketplace_lib.OAuth20Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.utils.enums.MeliStatusAccount;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.MeliResponseBodyException;
import uy.pepeganga.meli.service.repository.MeliAccountRepository;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    OAuth20Api auth20Api;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MeliAccountRepository meliAccountRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    IMeliService meliService;

    @Override
    public MeliAutheticationResponse getToken(String code) throws ApiException {
      //  String grantType = "authorization_code"; // or 'refresh_token' if you need get one new token
        String grantType = "authorization_code"; // or 'refresh_token' if you need get one new token
        String clientId = "5381382874135569"; // Your client_id
        String clientSecret = "ach1Wz5swsZTrQVJ9UTdKcAkg1Rsc0UA"; // Your client_secret
        String redirectUri = "https://localhost:4200/home/meli-accounts"; // Your redirect_uri
        String refreshToken = ""; // Your refresh_token

            return mapper.convertValue(auth20Api.getToken(grantType, clientId, clientSecret, redirectUri, code, refreshToken), MeliAutheticationResponse.class);

    }

    @Override
    public ResponseEntity<Map<String, Object>> updateAfterToken(Integer accountId, String code) {
        Map<String, Object> map = new HashMap<>();
        try {

            Optional<MeliAccount> accountFounded = meliAccountRepository.findById(accountId);
            if(accountFounded.isEmpty()){
                map.put("error",  new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                MeliAutheticationResponse meliAutheticationResponse = getToken(code);
                logger.info("By account id: {}, Meli token response: user id:  {}", accountId, meliAutheticationResponse.getUserId());
                MeliAccount meliAccountToUpdate = meliService.findAccountById(accountId);
                meliAccountToUpdate.setAccessToken(meliAutheticationResponse.getAccesToken());
                meliAccountToUpdate.setScope(meliAutheticationResponse.getScope());
                meliAccountToUpdate.setTokenType(meliAutheticationResponse.getTokenType());
                meliAccountToUpdate.setExpiresIn(meliAutheticationResponse.getExpiresIn());
                meliAccountToUpdate.setUserId(meliAutheticationResponse.getUserId());
                meliAccountToUpdate.setRefreshToken(meliAutheticationResponse.getRefreshToken());
                meliAccountToUpdate.setStatus(MeliStatusAccount.AUTHORIZED.getCode());
                map.put("response", meliAccountRepository.save(meliAccountToUpdate));
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (ApiException e) {
            try {
                logger.error(" Error getting token Meli Response: {}", e.getResponseBody());
                map.put("error-meli", new ApiMeliModelException(e.getCode(), e.getResponseBody(),  mapper.readValue(e.getResponseBody(), MeliResponseBodyException.class)));
            } catch (JsonProcessingException ex) {
                logger.error(" Error parsing Meli Response", ex);
                map.put("error",  new ApiMeliModelException(HttpStatus.PARTIAL_CONTENT.value(),  ex.getMessage()));
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }


}
