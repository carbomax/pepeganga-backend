package uy.com.pepeganga.uploadfileservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.uploadfileservice.services.StorageService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bucket")
@Validated
public class BucketController {

    @Autowired
    StorageService storageService;


    @GetMapping("/urls-by-sku-from-store")
    public ResponseEntity<Map<String, List<String>>> getUrlsBySku(@RequestParam @NotBlank String sku) throws PGException {
        return new ResponseEntity<>(storageService.getUrlsBySkuFromStore(sku ), HttpStatus.OK);
    }

    @GetMapping("/urls-by-sku-in-batch-from-store")
    public ResponseEntity<Map<String, List<String>>> getUrlsBySkuInBatch(@Valid @RequestParam @Size(min = 1, max = 100) List<String> skus) throws PGException {
        return new ResponseEntity<>(storageService.getUrlsBySkuInBatchFromStore(skus), HttpStatus.OK);
    }


    @GetMapping("/download-file-from-store-bucket")
    public ResponseEntity<ByteArrayResource> downloadFileFromStore(@RequestParam String pathFile) throws PGException {
        byte[] file = storageService.downloadFileFromStore(pathFile);
        ByteArrayResource resource = new ByteArrayResource(file);
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + pathFile + "\"")
                .body(resource);
    }

    @GetMapping("/download-file-from-upload-bucket")
    public ResponseEntity<ByteArrayResource> downloadFileFromUploadBucket(@RequestParam String pathFile) throws PGException {
        byte[] file = storageService.downloadFileFromUploadBucket(pathFile);
        ByteArrayResource resource = new ByteArrayResource(file);
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + pathFile + "\"")
                .body(resource);
    }

    @GetMapping("/transfer-objects-buckets-products-to-upload")
    public ResponseEntity<Map<String, List<String>>> transferObjectsBucketsProductsToUpload(@RequestParam String sku, @RequestParam int profileId, @RequestParam int marketplace) throws PGException {
        return new ResponseEntity<>(storageService.transferObjectsBucketsProductsAndUpload(sku, profileId, marketplace), HttpStatus.OK);
    }

    @GetMapping("/urls-by-profile-marketplace-sku")
    public ResponseEntity<Map<String, List<String>>> getUrlsByProfileMarketplaceSku(@RequestParam String folder, @RequestParam String sku, @RequestParam int profileId, @RequestParam int marketplace) throws PGException {
        return new ResponseEntity<>(storageService.getUrlsByProfileMarketplaceSku(folder,sku, profileId, marketplace), HttpStatus.OK);
    }

    @PostMapping("/upload-file-to-legacy")
    public ResponseEntity<String> uploadFileToLegacy(@RequestBody MultipartFile multipartFile) throws PGException {
        return new ResponseEntity<>(storageService.uploadFileToLegacy(multipartFile), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-file-from-upload-bucket")
    public ResponseEntity<Boolean> deleteFileFromUploadBucket(@RequestParam String pathFile) throws PGException {
        return new ResponseEntity<>(storageService.deleteFileFromUploadBucket(pathFile), HttpStatus.OK);
    }

    @DeleteMapping("/delete-files-list-from-upload-bucket")
    public ResponseEntity<Boolean> deleteFileListFromUploadBucket(@RequestParam List<String> pathFiles) throws PGException {
        return new ResponseEntity<>(storageService.deleteFileListFromUploadBucket(pathFiles), HttpStatus.OK);
    }
}
