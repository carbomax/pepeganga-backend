package uy.com.pepeganga.uploadfileservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.uploadfileservice.client.MeliClient;
import uy.com.pepeganga.uploadfileservice.exceptions.ReportError;

import java.util.List;

@Service
@Slf4j
public class MeliServiceImpl implements MeliService {

    private static final String PUBLICATION_REPORT_NAME = "meli_publications.jrxml";
    private static final String PUBLICATION_REPORT_SHEET_NAME = "productos publicados";

    @Autowired
    MeliClient meliClient;

    @Autowired
    JasperReportService jasperReportService;

    public byte[] exportPublications(String exportType, List<Integer> ids, Integer profileId) throws PGException {
        try {
            return jasperReportService.exportReport(JasperReportService.ExportType.valueOf(exportType), PUBLICATION_REPORT_NAME, PUBLICATION_REPORT_SHEET_NAME, meliClient.reportPublications(profileId, ids));
        } catch (Exception e) {
            log.error(String.format("Error exporting file: %s", PUBLICATION_REPORT_NAME), e);
            throw new ReportError(String.format("Error exporting file: %s", PUBLICATION_REPORT_NAME), HttpStatus.CONFLICT, e.getMessage());
        }
    }


}
