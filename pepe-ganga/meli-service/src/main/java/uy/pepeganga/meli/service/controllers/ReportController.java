package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.pepeganga.meli.service.services.IMeliReportsService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {

    @Autowired
    private IMeliReportsService meliReportsService;

    @PostMapping("/download-publications")
    public ResponseEntity<ByteArrayResource> downloadPublications(@RequestParam(defaultValue = "publication_report.xlsx") String fileName,
                                                                          @RequestParam(defaultValue = "EXCEL") String exportType,
                                                                          @RequestParam int profileId,
                                                                          @RequestBody(required = false) List<Integer> ids) throws PGException {
        ByteArrayResource resource = new ByteArrayResource(meliReportsService.exportPublications(exportType, ids, profileId));
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
