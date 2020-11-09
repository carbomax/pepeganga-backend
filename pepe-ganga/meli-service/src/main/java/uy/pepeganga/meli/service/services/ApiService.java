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
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.com.pepeganga.business.common.utils.date.DateTimePlusType;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.business.common.utils.enums.MeliStatusAccount;
import uy.pepeganga.meli.service.exceptions.TokenException;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;
import uy.pepeganga.meli.service.models.MeliResponseBodyException;
import uy.pepeganga.meli.service.models.MeliUserAccount;
import uy.pepeganga.meli.service.models.publications.ChangeStatusPublicationRequest;
import uy.pepeganga.meli.service.models.publications.DescriptionRequest;
import uy.pepeganga.meli.service.models.publications.PropertiesWithSalesRequest;
import uy.pepeganga.meli.service.models.publications.PropertiesWithoutSalesRequest;
import uy.pepeganga.meli.service.repository.ProfileRepository;
import uy.pepeganga.meli.service.repository.SellerAccountRepository;
import uy.pepeganga.meli.service.utils.ApiResources;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

            SellerAccount sellerAccountToUpdate = meliService.findAccountById(accountId);
            if (Objects.isNull(sellerAccountToUpdate)) {
                map.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                MeliAutheticationResponse meliAutheticationResponse = getTokenByCode(code);
                logger.info("By account id: {}, Meli token response: user id:  {}", accountId, meliAutheticationResponse.getUserId());
                if (sellerAccountToUpdate.getUserId() != null && !sellerAccountToUpdate.getUserId().equals(meliAutheticationResponse.getUserId())) {
                    logger.error("Account with id: {} not acceptable with meli user: {} and seller account: {}", accountId, meliAutheticationResponse.getUserId(), sellerAccountToUpdate.getUserId());
                    map.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_ACCEPTABLE.value(), String.format("Account with id: %s not acceptable", accountId)));
                } else if (sellerAccountToUpdate.getUserId() == null && sellerAccountRepository.findByUserId(meliAutheticationResponse.getUserId()) != null) {
                    logger.error("Exist a account with userMeliIdL: {}", meliAutheticationResponse.getUserId());
                    map.put(ERROR, new ApiMeliModelException(HttpStatus.CONFLICT.value(), String.format("Exist a account with userMeliIdL: %s", meliAutheticationResponse.getUserId())));
                } else {
                    // Updating account
                    sellerAccountToUpdate.setAccessToken(meliAutheticationResponse.getAccesToken());
                    sellerAccountToUpdate.setScope(meliAutheticationResponse.getScope());
                    sellerAccountToUpdate.setTokenType(meliAutheticationResponse.getTokenType());
                    sellerAccountToUpdate.setExpiresIn(meliAutheticationResponse.getExpiresIn());
                    sellerAccountToUpdate.setUserId(meliAutheticationResponse.getUserId());
                    sellerAccountToUpdate.setRefreshToken(meliAutheticationResponse.getRefreshToken());
                    sellerAccountToUpdate.setStatus(MeliStatusAccount.AUTHORIZED.getCode());
                    sellerAccountToUpdate.setExpirationDate(DateTimeUtilsBss.plusCurrentTimeMilleSeconds(meliAutheticationResponse.getExpiresIn(), DateTimePlusType.SECOND));
                    map.put("response", sellerAccountRepository.save(sellerAccountToUpdate));
                }

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
    public Map<String, Object> synchronizeAccount(Integer accountId) {

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
                } catch (JsonProcessingException ex) {
                    logger.error(" Error parsing Meli Response", ex);
                    map.put(ERROR, new ApiMeliModelException(HttpStatus.PARTIAL_CONTENT.value(), ex.getMessage()));
                }
            }

        }

        return map;
    }

    @Override
    public SellerAccount getTokenByRefreshToken(SellerAccount account) throws TokenException {


        MeliAutheticationResponse meliAutheticationResponse;
        try {
            meliAutheticationResponse = mapper.convertValue(auth20Api.getToken("refresh_token", clientId, clientSecret, "", "", account.getRefreshToken()), MeliAutheticationResponse.class);
            logger.info("By account id: {}, Meli refresh token response: user id:  {}", account.getId(), account.getUserId());
            account.setAccessToken(meliAutheticationResponse.getAccesToken());
            account.setScope(meliAutheticationResponse.getScope());
            account.setTokenType(meliAutheticationResponse.getTokenType());
            account.setExpiresIn(meliAutheticationResponse.getExpiresIn());
            account.setUserId(meliAutheticationResponse.getUserId());
            account.setRefreshToken(meliAutheticationResponse.getRefreshToken());
            account.setExpirationDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis());
            account.setStatus(MeliStatusAccount.SYNCHRONIZED.getCode());

            return sellerAccountRepository.save(account);
        } catch (Exception e) {
            if(e.getCause() instanceof  ApiException){
                throw  new TokenException(((ApiException) e.getCause()).getCode(),((ApiException) e.getCause()).getResponseBody(), e.getCause());
            }
           throw  new TokenException(e.getMessage(), e);
        }

    }


    @Override
    public Object createPublication(Item publicationRequest, String token) throws ApiException {
        return restClientApiUy.resourcePost(ApiResources.ITEMS, token, publicationRequest);
    }

    @Override
    public Object getOrderByNotificationResource(String notificationResource, String token) throws ApiException {
        return restClientApiUy.resourceGet(notificationResource, token);
    }

    @Override
    public Object getOrdersBySeller(Long sellerId, String token) throws ApiException {
        return restClientApiUy.resourceGet(String.format(ApiResources.ORDERS + "/search?seller=%d", sellerId), token);
    }

    @Override
    public Object updatePropertiesWithoutSales(PropertiesWithoutSalesRequest request, String token, String idPublicationMeli) throws ApiException {
        return restClientApiUy.resourcePut(String.format(ApiResources.ITEMS + "/%s", idPublicationMeli), token, request);
    }

    @Override
    public Object changeStatusPublications(ChangeStatusPublicationRequest request, String token, String idPublicationMeli) throws ApiException {
        return restClientApiUy.resourcePut(String.format(ApiResources.ITEMS + "/%s", idPublicationMeli), token, request);
    }

    @Override
    public Object updatePropertiesWithSales(PropertiesWithSalesRequest request, String token, String idPublicationMeli) throws ApiException {
        return restClientApiUy.resourcePut(String.format(ApiResources.ITEMS + "/%s", idPublicationMeli), token, request);
    }

    @Override
    public Object updateDescription(DescriptionRequest request, String token, String idPublicationMeli) throws ApiException {
        return restClientApiUy.resourcePut(String.format(ApiResources.ITEMS + "/%s" + "/description?api_version=2", idPublicationMeli), token, request);
    }
}



