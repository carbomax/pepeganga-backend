package uy.pepeganga.meli.service.services;

import meli.ApiException;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.models.MeliAutheticationResponse;

public interface IAuthService {

    MeliAutheticationResponse getToken(Integer profileId, String code) throws ApiException;

    MeliAccount createAccountByProfile(Integer profileId, MeliAccount meliAccount);
}
