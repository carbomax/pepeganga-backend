package uy.pepeganga.meli.service.services;

import meli.model.Item;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.DetailsPublicationsMeliGrid;
import uy.pepeganga.meli.service.models.ItemModel;

import java.util.List;
import java.util.Map;

public interface IMeliService {

    SellerAccount createAccountByProfile(Integer profileId, SellerAccount sellerAccount);

    SellerAccount updateMeliAccount(Integer accountId, SellerAccount sellerAccount);

    void deleteMeliAccount(Integer accountId);

    List<SellerAccount> meliAccountsByProfileId(Integer profileId);

    SellerAccount findAccountById(Integer accountId);

    Map<String, Object> createPublication(Item publicationRequest, Integer account);

    List<Map<String, Object>> createPublicationList(List<Item> items, Integer accountId ) throws Exception;

    boolean createPublicationsFlow(List<ItemModel> items, Integer accountId, Short idMargin) throws NoSuchFieldException;

    boolean createOrUpdateDetailPublicationsMeli(List<ItemModel> items, Integer accountId, Short idMargin);

    DetailsPublicationsMeliGrid RepublishProduct(DetailsPublicationsMeliGrid product);
}
