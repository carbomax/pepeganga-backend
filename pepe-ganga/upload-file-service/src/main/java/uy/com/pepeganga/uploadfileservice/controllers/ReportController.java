package uy.com.pepeganga.uploadfileservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.uploadfileservice.services.MeliService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {


    @Autowired
    MeliService meliService;

    @PostMapping("/export-publications")
    public ResponseEntity<ByteArrayResource> downloadPublications(@RequestParam(defaultValue = "publication_report.xlsx") String fileName,
                                                                  @RequestParam(defaultValue = "EXCEL") String exportType,
                                                                  @RequestParam int profileId,
                                                                  @RequestBody(required = false) List<Integer> ids) throws PGException {
        ByteArrayResource resource = new ByteArrayResource(meliService.exportPublications(exportType, ids, profileId));
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
