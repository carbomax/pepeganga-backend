package uy.pepeganga.meli.service.services;

import meli.model.Item;
import uy.com.pepeganga.business.common.entities.SellerAccount;

import java.util.List;
import java.util.Map;

public interface IMeliService {

    SellerAccount createAccountByProfile(Integer profileId, SellerAccount sellerAccount);

    SellerAccount updateMeliAccount(Integer accountId, SellerAccount sellerAccount);

    void deleteMeliAccount(Integer accountId);

    List<SellerAccount> meliAccountsByProfileId(Integer profileId);

    SellerAccount findAccountById(Integer accountId);

    Map<String, Object> createPublication(Item publicationRequest, Integer account);

}
