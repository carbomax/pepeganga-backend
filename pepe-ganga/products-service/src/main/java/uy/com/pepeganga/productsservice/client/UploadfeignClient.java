package uy.com.pepeganga.productsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uy.com.pepeganga.business.common.exceptions.PGException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@FeignClient(name = "upload-file-service")
public interface UploadfeignClient {

    @GetMapping("/api/bucket/urls-by-sku-from-store")
    Map<String, List<String>> getUrlsBySku(@RequestParam @NotBlank String sku) throws PGException;

    @GetMapping("/api/bucket/urls-by-sku-in-batch-from-store")
    Map<String, List<String>> getUrlsBySkuInBatch(@Valid @RequestParam @Size(min = 1, max = 100) List<String> skus) throws PGException;

    @GetMapping("/api/bucket/download-file-from-store-bucket")
    ByteArrayResource downloadFileFromStore(@RequestParam String pathFile) throws PGException;

    @GetMapping("/api/bucket/download-file-from-upload-bucket")
    ByteArrayResource downloadFileFromUploadBucket(@RequestParam String pathFile) throws PGException;

    @GetMapping("/api/bucket/transfer-objects-buckets-products-to-upload")
    Map<String, List<String>> transferObjectsBucketsProductsToUpload(@RequestParam String sku, @RequestParam int profileId, @RequestParam int marketplace) throws PGException;

    @GetMapping("/api/bucket/urls-by-profile-marketplace-sku")
    ResponseEntity<Map<String, List<String>>> getUrlsByProfileMarketplaceSku(@RequestParam String folder, @RequestParam String sku, @RequestParam int profileId, @RequestParam int marketplace) throws PGException;

    @PostMapping("/api/bucket/upload-file-to-legacy")
    ResponseEntity<String> uploadFileToLegacy(@RequestParam MultipartFile multipartFile) throws PGException;

    @DeleteMapping("/api/bucket/delete-file-from-upload-bucket")
    ResponseEntity<Boolean> deleteFileFromUploadBucket(@RequestParam String pathFile) throws PGException;

}
