package uy.pepeganga.meli.service.services;

import meli.ApiException;
import meli.model.Item;
import meli.model.UpdateTitleAndPriceRequest;
import org.springframework.http.ResponseEntity;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;

import java.util.Map;

public interface IApiService {

    MeliAutheticationResponse getTokenByCode(String code) throws ApiException;

    ResponseEntity<Map<String, Object>> updateAfterToken(Integer profileId, String code);

    SellerAccount getTokenByRefreshToken(SellerAccount account) throws ApiException;

    Map<String, Object> synchronizeAccount(Integer accountId);

    Object createPublication(Item publicationRequest, String token) throws ApiException;

    Object getOrderByNotificationResource(String orderId, String token) throws ApiException;

    Object getOrdersBySeller(Long seller, String token) throws ApiException;

    Object updateTitleAndPrice(UpdateTitleAndPriceRequest request, String token, String idPublicationMeli) throws ApiException;
}
