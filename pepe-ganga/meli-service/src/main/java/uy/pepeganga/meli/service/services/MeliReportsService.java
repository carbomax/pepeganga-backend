package uy.pepeganga.meli.service.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.business.common.utils.enums.MeliStatusPublications;
import uy.pepeganga.meli.service.exceptions.NotFoundException;
import uy.pepeganga.meli.service.exceptions.ReportError;
import uy.pepeganga.meli.service.models.dto.reports.PublicationsMeliDto;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeliReportsService implements IMeliReportsService {

    private static final String PUBLICATION_REPORT_NAME = "meli_publications.jrxml";
    private static final String PUBLICATION_REPORT_SHEET_NAME = "publications_report";

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ReportService reportService;

    public byte[] exportPublications(String exportType, List<Integer> ids, Integer profileId) throws PGException {
        try {
            if (Objects.isNull(ids) || ids.size() <= 0) {
                Optional<Profile> profileFounded = profileRepository.findById(profileId);
                if (profileFounded.isPresent()) {
                    List<Integer> idsFounded = detailsPublicationRepository.findIdsByMeliAccountsOfProfileId(profileFounded.get().getId());
                    return reportService.exportReport(ReportService.ExportType.valueOf(exportType), PUBLICATION_REPORT_NAME, PUBLICATION_REPORT_SHEET_NAME, getPublicationsReports(idsFounded));
                } else {
                    throw new NotFoundException(String.format("Profile id: %d", profileId), HttpStatus.NOT_FOUND);
                }
            }
            return reportService.exportReport(ReportService.ExportType.valueOf(exportType), PUBLICATION_REPORT_NAME, PUBLICATION_REPORT_SHEET_NAME, getPublicationsReports(ids));
        } catch (Exception e) {
            log.error(String.format("Error exporting file: %s", PUBLICATION_REPORT_NAME), e);
            throw new ReportError(String.format("Error exporting file: %s", PUBLICATION_REPORT_NAME), HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private List<PublicationsMeliDto> getPublicationsReports(List<Integer> ids) {
        return detailsPublicationRepository.publicationReport(ids).stream()
                .map(p -> PublicationsMeliDto.builder()
                        .idMLPublication(p.getIdMLPublication())
                        .accountBusinessName(p.getAccountBusinessName())
                        .currentStock(p.getCurrentStock())
                        .flex(p.getFlex() == 1 ? "SI" : "NO")
                        .lastUpgrade(p.getLastUpgrade())
                        .pricePublication(p.getPricePublication())
                        .permalink(p.getPermalink())
                        .saleStatus(p.getSaleStatus())
                        .sku(p.getSku())
                        .status(MeliStatusPublications.of(p.getStatus()).getReportValue())
                        .title(p.getTitle())
                        .build()

                ).collect(Collectors.toList());
    }

}
