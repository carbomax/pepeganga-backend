package uy.pepeganga.meli.service.services;

import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.business.common.models.PublicationsMeliDto;

import java.util.List;

@Service
public interface IMeliReportsService {

    byte[] exportPublications(String exportType, List<Integer> ids, Integer profileId) throws PGException;

    List<PublicationsMeliDto> reportPublications(List<Integer> ids, Integer profileId) throws PGException;

}
