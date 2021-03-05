package uy.com.pepeganga.uploadfileservice.services;

import org.springframework.web.multipart.MultipartFile;
import uy.com.pepeganga.business.common.exceptions.PGException;

import java.util.List;
import java.util.Map;

public interface StorageService {

    Map<String,  List<String>> getUrlsBySkuFromStore(String sku) throws PGException;

    Map<String, List<String>> getUrlsBySkuInBatchFromStore(List<String> skus) throws PGException;

    byte [] downloadFileFromStore(String fileName) throws PGException;

    Map<String, List<String>> transferObjectsBucketsProductsAndUpload(String sku, int profileId, int marketplace) throws PGException;

    Map<String, List<String>> getUrlsByProfileMarketplaceSku(String folder, String sku, int profileId, int marketplace) throws PGException;

    byte[] downloadFileFromUploadBucket(String pathFile) throws PGException;

    String uploadFileToLegacy(MultipartFile multipartFile) throws PGException;

    boolean deleteFileFromUploadBucket(String fileName) throws PGException;
}
