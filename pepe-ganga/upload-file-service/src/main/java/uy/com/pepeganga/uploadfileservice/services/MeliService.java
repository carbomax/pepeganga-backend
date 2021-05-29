package uy.com.pepeganga.uploadfileservice.services;

import uy.com.pepeganga.business.common.exceptions.PGException;

import java.util.List;


public interface MeliService {

    byte[] exportPublications(String exportType, List<Integer> ids, Integer profileId) throws PGException;

}
