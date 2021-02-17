package uy.pepeganga.meli.service.services;

import meli.ApiException;
import meli.model.Item;
import org.springframework.http.ResponseEntity;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.exceptions.TokenException;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;
import uy.pepeganga.meli.service.models.meli_account_configuration.QueryRequest;
import uy.pepeganga.meli.service.models.publications.*;

import java.util.Map;

public interface IApiService {

    MeliAutheticationResponse getTokenByCode(String code) throws ApiException;

    ResponseEntity<Map<String, Object>> updateAfterToken(Integer profileId, String code);

    SellerAccount getTokenByRefreshToken(SellerAccount account) throws ApiException, TokenException;

    Map<String, Object> synchronizeAccount(Integer accountId);

    Object createPublication(Item publicationRequest, String token) throws ApiException;

    Object getOrderByNotificationResource(String orderId, String token) throws ApiException;

    Object getShipmentOfOrder(Long shipmentId, String token) throws ApiException;

    Object getOrdersBySeller(Long seller, String token) throws ApiException;

    Object updateDescription(DescriptionRequest request, String token, String idPublicationMeli) throws ApiException;

    Object updatePropertiesWithSales(PropertiesWithSalesRequest request, String token, String idPublicationMeli) throws ApiException;

    Object updatePricePublication(ChangePriceRequest request, String token, String idPublicationMeli) throws ApiException;

    Object updatePropertiesWithoutSales(PropertiesWithoutSalesRequest request, String token, String idPublicationMeli) throws ApiException;

    Object changeStatusPublications(ChangeStatusPublicationRequest request, String token, String idPublicationMeli) throws ApiException;

    Object deletePublication(DeletePublicationRequest request, String token, String idPublicationMeli) throws ApiException;

    Object republishPublication(RepublishPublicationRequest request, String token, String idPublicationMeli) throws ApiException;

    Object updateStock(ChangeStockRequest request, String token, String idPublicationMeli) throws ApiException;

    Map<String, Object> getStatusPublication(String idPublication);

    Object isFlexInItem(String idPublicationMeli, String token) throws ApiException;

    Object disableFlexInItem(String idPublicationMeli, String token) throws ApiException;

    Object showConfigurationSeller(QueryRequest request, String token) throws ApiException;

}
