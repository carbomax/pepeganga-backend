package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import meli.model.Item;
import meli_marketplace_lib.OAuth20Api;
import meli_marketplace_lib.RestClientApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.utils.enums.MeliStatusAccount;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.MeliResponseBodyException;
import uy.pepeganga.meli.service.models.MeliUserAccount;
import uy.pepeganga.meli.service.repository.SellerAccountRepository;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ApiService implements IApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Value("${meli.api.client}")
    private String clientId;

    @Value("${meli.api.secret}")
    private String clientSecret;

    @Value("${meli.api.redirect}")
    private String redirectUri;


    @Autowired
    OAuth20Api auth20Api;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    SellerAccountRepository sellerAccountRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    IMeliService meliService;

    @Autowired
    @Qualifier("restClientApiUy")
    RestClientApi restClientApiUy;

    private static final String ERROR = "error";
    private static final String MELI_ERROR = "error_meli";

    @Override
    public MeliAutheticationResponse getTokenByCode(String code) throws ApiException {

        String grantType = "authorization_code";

        return mapper.convertValue(auth20Api.getToken(grantType, clientId, clientSecret, redirectUri, code, ""), MeliAutheticationResponse.class);

    }

    @Override
    public ResponseEntity<Map<String, Object>> updateAfterToken(Integer accountId, String code) {
        Map<String, Object> map = new HashMap<>();
        try {

            Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
            if (accountFounded.isEmpty()) {
                map.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                MeliAutheticationResponse meliAutheticationResponse = getTokenByCode(code);
                logger.info("By account id: {}, Meli token response: user id:  {}", accountId, meliAutheticationResponse.getUserId());
                SellerAccount sellerAccountToUpdate = meliService.findAccountById(accountId);
                sellerAccountToUpdate.setAccessToken(meliAutheticationResponse.getAccesToken());
                sellerAccountToUpdate.setScope(meliAutheticationResponse.getScope());
                sellerAccountToUpdate.setTokenType(meliAutheticationResponse.getTokenType());
                sellerAccountToUpdate.setExpiresIn(meliAutheticationResponse.getExpiresIn());
                sellerAccountToUpdate.setUserId(meliAutheticationResponse.getUserId());
                sellerAccountToUpdate.setRefreshToken(meliAutheticationResponse.getRefreshToken());
                sellerAccountToUpdate.setStatus(MeliStatusAccount.AUTHORIZED.getCode());

                map.put("response", sellerAccountRepository.save(sellerAccountToUpdate));
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (ApiException e) {
            try {
                logger.error(" Error getting token Meli Response: {}", e.getResponseBody());
                map.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody(), mapper.readValue(e.getResponseBody(), MeliResponseBodyException.class)));
            } catch (JsonProcessingException ex) {
                logger.error(" Error parsing Meli Response", ex);
                map.put(ERROR, new ApiMeliModelException(HttpStatus.PARTIAL_CONTENT.value(), ex.getMessage()));
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SellerAccount getTokenByRefreshToken(SellerAccount account) throws ApiException {


        MeliAutheticationResponse meliAutheticationResponse = mapper.convertValue(auth20Api.getToken("refresh_token", clientId, clientSecret, "", "", account.getRefreshToken()), MeliAutheticationResponse.class);
        logger.info("By account id: {}, Meli refresh token response: user id:  {}", account.getId(), account.getUserId());
        account.setAccessToken(meliAutheticationResponse.getAccesToken());
        account.setScope(meliAutheticationResponse.getScope());
        account.setTokenType(meliAutheticationResponse.getTokenType());
        account.setExpiresIn(meliAutheticationResponse.getExpiresIn());
        account.setUserId(meliAutheticationResponse.getUserId());
        account.setRefreshToken(meliAutheticationResponse.getRefreshToken());
        account.setStatus(MeliStatusAccount.SYNCHRONIZED.getCode());

        return sellerAccountRepository.save(account);
    }

    @Override
    public Map<String, Object> synchronizeAccount(Integer accountId) {


        //  https://api.mercadolibre.com/users/$USER_ID?access_token=¢ACCESS_TOKEN

        Map<String, Object> map = new HashMap<>();
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
        if (accountFounded.isEmpty()) {
            map.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
        } else {
            try {
                String resource = "/users/" + accountFounded.get().getUserId();
                MeliUserAccount meliUserAccount = mapper.convertValue(restClientApiUy.resourceGet(resource, accountFounded.get().getAccessToken().trim()), MeliUserAccount.class);
                SellerAccount sellerAccountToUpdate = accountFounded.get();
                sellerAccountToUpdate.setStatus(MeliStatusAccount.SYNCHRONIZED.getCode());
                sellerAccountToUpdate.setEmail(meliUserAccount.getEmail());
                sellerAccountToUpdate.setNickname(meliUserAccount.getNickname());
                sellerAccountToUpdate.setSiteId(meliUserAccount.getSiteId());
                sellerAccountToUpdate.setUserType(meliUserAccount.getUserType());
                sellerAccountToUpdate.setPoints(meliUserAccount.getPoints());
                map.put("response", sellerAccountRepository.save(sellerAccountToUpdate));
            } catch (ApiException e) {

                try {
                    logger.error(" Error getting token Meli Response: {}", e.getResponseBody());
                    map.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody(), mapper.readValue(e.getResponseBody(), MeliResponseBodyException.class)));
                }catch (JsonProcessingException ex) {
                    logger.error(" Error parsing Meli Response", ex);
                    map.put(ERROR, new ApiMeliModelException(HttpStatus.PARTIAL_CONTENT.value(), ex.getMessage()));
                }
            }

        }

        return map;
    }

    @Override
    public Object createPublication(Item publicationRequest, String token) throws ApiException {

            return  restClientApiUy.resourcePost("items", token, publicationRequest);

    }

}



