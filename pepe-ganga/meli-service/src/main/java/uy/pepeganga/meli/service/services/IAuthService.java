package uy.pepeganga.meli.service.services;

import meli.ApiException;
import org.springframework.http.ResponseEntity;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;

import java.util.Map;

public interface IAuthService {

    MeliAutheticationResponse getTokenByCode(String code) throws ApiException;

    ResponseEntity<Map<String, Object>> updateAfterToken(Integer profileId, String code);

    MeliAccount getTokenByRefreshToken(MeliAccount account) throws ApiException;

    Map<String, Object> synchronizeAccount(Integer accountId);
}
