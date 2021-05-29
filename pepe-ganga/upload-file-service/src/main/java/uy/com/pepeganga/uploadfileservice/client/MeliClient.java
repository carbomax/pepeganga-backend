package uy.com.pepeganga.uploadfileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.business.common.models.PublicationsMeliDto;

import java.util.List;

@FeignClient(name = "meli-service")
public interface MeliClient {

    @PostMapping("/api/reports/report-publications")
    List<PublicationsMeliDto> reportPublications(@RequestParam int profileId, @RequestBody(required = false) List<Integer> ids) throws PGException;
}
