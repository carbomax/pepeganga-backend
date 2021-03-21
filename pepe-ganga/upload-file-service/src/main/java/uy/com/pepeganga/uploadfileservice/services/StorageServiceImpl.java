package uy.com.pepeganga.uploadfileservice.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.businessutils.enums.CopyBucketResultType;
import uy.com.pepeganga.uploadfileservice.exceptions.AwsBucketException;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StorageServiceImpl implements StorageService {

    Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    HttpServlet httpServlet;

    @Value("${application.buckets.products}")
    private String bucketProducts;

    @Value("${application.buckets.upload}")
    private String bucketUpload;

    private static final String MY_PRODUCTS_FOLDER = "MY_PRODUCTS";

    @Autowired
    AmazonS3 s3Client;

    @Override
    public Map<String, List<String>> getUrlsBySkuFromStore(String sku) throws PGException {
        Map<String, List<String>> skuUrls = new HashMap<>();
        skuUrls.put(sku, getUrlsBySku(bucketProducts, sku));
        return skuUrls;
    }
    @Override
    public Map<String, List<String>> getUrlsBySkuInBatchFromStore(List<String> skus) throws PGException {
       return this.getUrlsBySkuInBatch(skus, bucketProducts);
    }


    @Override
    public byte[] downloadFileFromStore(String pathFile) throws PGException {
        return downloadFile(bucketProducts, pathFile);
    }

    @Override
    public byte[] downloadFileFromUploadBucket(String pathFile) throws PGException {
        return downloadFile(bucketUpload, pathFile);
    }


    @Override
    public Map<String, List<String>> transferObjectsBucketsProductsAndUpload(String sku, int profileId, int marketplace) throws PGException {

        Map<String, List<String>> result = new HashMap<>();
        List<String> downloadUrls =  new ArrayList<>();
        this.getKeysByPrefix(bucketProducts, sku.concat("/")).forEach(s -> {

            String pathDestinationKey = MY_PRODUCTS_FOLDER.concat("/").concat(String.valueOf(profileId)).concat("/").concat(String.valueOf(marketplace)).concat("/").concat(s);

            CopyBucketResultType resultType = copyObjectBetweenBuckets(bucketProducts, s, bucketUpload, pathDestinationKey);
            if (resultType.equals(CopyBucketResultType.SUCCESS)) {
                downloadUrls.add(pathDestinationKey);
            } else {
                downloadUrls.add(resultType.name());
            }

        });
        result.put(sku, downloadUrls);
        return result;

    }

    @Override
    public Map<String, List<String>> getUrlsByProfileMarketplaceSku(String folder, String sku, int profileId, int marketplace) throws PGException {

        Map<String, List<String>> skuUrls = new HashMap<>();
        skuUrls.put(sku, this.getKeysByPrefix(bucketUpload,
                folder.concat("/").concat(String.valueOf(profileId)).concat("/").concat(String.valueOf(marketplace)).concat("/").concat(sku).concat("/")));
        return skuUrls;

    }


    @Override
    public String uploadFileToLegacy(MultipartFile multipartFile) throws PGException {

        String target;
        File fileToBucket = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream outputStream = new FileOutputStream(fileToBucket)){
            outputStream.write(multipartFile.getBytes());
            String fileName = UUID.randomUUID().toString().concat("-").concat(fileToBucket.getName());
            target = "LEGACY".concat("/").concat(fileName);
            s3Client.putObject(new PutObjectRequest(bucketUpload, target, fileToBucket));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AwsBucketException(String.format("File: %s not uploaded", multipartFile.getOriginalFilename()), HttpStatus.NOT_MODIFIED);
        } finally {
            try {
                Files.delete(Paths.get(fileToBucket.getAbsolutePath()));
            } catch (IOException e) {
                logger.warn("File {} local not deleted after putObject success in aws bucket", fileToBucket.getAbsolutePath());
            }

        }

        return target;
    }

    @Override
    public boolean deleteFileFromUploadBucket(String fileName) throws PGException {
        try {
            s3Client.deleteObject(bucketUpload, fileName);
            return true;
        } catch (AmazonClientException e){
            throw new AwsBucketException(String.format("File: %s not deleted", fileName), HttpStatus.NOT_MODIFIED, e.getMessage());
        }

    }

    private Map<String, List<String>> getUrlsBySkuInBatch(List<String> skus, String bucket) {
        Map<String, List<String>> skuUrls = new HashMap<>();
        skus.forEach(s -> {
            try {
                skuUrls.put(s, getUrlsBySku(bucket, s));
            } catch (AwsBucketException e) {
                logger.error(e.getMessage(), e);
                skuUrls.put(s, Collections.singletonList(e.getError()));
            }
        });
        return skuUrls;
    }

    private byte[] downloadFile(String bucket, String fileName) throws PGException {

        if(!s3Client.doesObjectExist(bucket, fileName)){
            throw new AwsBucketException(String.format("File: %s not exist in bucket: %s", fileName, bucket), HttpStatus.NOT_FOUND);
        }
        try (S3Object s3Object = s3Client.getObject(bucket, fileName)) {
            S3ObjectInputStream content = s3Object.getObjectContent();
            return IOUtils.toByteArray(content);
        } catch (AmazonClientException e) {
            throw new AwsBucketException(String.format("Request bucket: %s throw a exception", bucket), HttpStatus.BAD_REQUEST, e.getCause().getMessage());

        } catch (IOException e) {
            throw new AwsBucketException("Error converting bucket file to byte array", HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        }

    }

    private List<String> getUrlsBySku(String bucket, String prefix) throws AwsBucketException {
        return this.getKeysByPrefix(bucket, prefix.concat("/"));
    }

    private List<String> getKeysByPrefix(String bucket, String prefix) throws AwsBucketException {
        List<String> urls = new ArrayList<>();
        try {
            List<S3ObjectSummary> summaries = s3Client.listObjects(bucket, prefix).getObjectSummaries();
            summaries.forEach(s3ObjectSummary -> {
                if (!s3ObjectSummary.getKey().endsWith("/")) {
                    urls.add(s3ObjectSummary.getKey());
                }
            });

            return urls;
        } catch (AmazonClientException e) {
            throw new AwsBucketException(String.format("Request bucket: %s throw a exception", bucketProducts), HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        }
    }


    private CopyBucketResultType copyObjectBetweenBuckets(String sourceBucketName, String sourceKey,
                                                          String destinationBucketName, String destinationKey) {

        try {
            if (s3Client.doesObjectExist(sourceBucketName, sourceKey)) {
                s3Client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
                return CopyBucketResultType.SUCCESS;
            } else {
                return CopyBucketResultType.TARGET_NOT_FOUNDED;
            }
        } catch (AmazonClientException e) {
            logger.info("Dont copied by exception: {}", e.getMessage());
            return CopyBucketResultType.NOT_COPIED;
        }


    }
}
