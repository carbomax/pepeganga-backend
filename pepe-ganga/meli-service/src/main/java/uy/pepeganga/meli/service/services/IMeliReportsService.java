package uy.pepeganga.meli.service.services;

import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.exceptions.PGException;

import java.util.List;

@Service
public interface IMeliReportsService {

    byte[] exportPublications(String exportType, List<Integer> ids, Integer profileId) throws PGException;

}
