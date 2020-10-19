package uy.pepeganga.meli.service.services;

import uy.com.pepeganga.business.common.entities.MeliAccount;

import java.util.List;

public interface IMeliService {

    MeliAccount createAccountByProfile(Integer profileId, MeliAccount meliAccount);

    MeliAccount updateMeliAccount(Integer accountId, MeliAccount meliAccount);

    void deleteMeliAccount(Integer accountId);

    List<MeliAccount> meliAccountsByProfileId(Integer profileId);

    MeliAccount findAccountById(Integer accountId);

}
