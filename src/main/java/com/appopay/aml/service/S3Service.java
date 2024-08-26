package com.appopay.aml.service;
import com.appopay.aml.Exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    @Value("${accessKeyId}")
    private String accessKeyId = "AKIAUFQFSYCH7HUDE4VL";
    @Value("${secretAccessKey}")
    private String secretAccessKey = "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";

    public S3Service() {
        String bucketName = "appopay-aml-frontend";
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_2)  // Set your desired region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile multipartFile, String keyName) {
        try {
            // Convert MultipartFile to a file
            Path tempFile = Files.createTempFile(keyName, multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile.toFile());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile));
            // Clean up the temp file
            Files.delete(tempFile);

            return response.eTag(); // Return the ETag as a confirmation of successful upload
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to upload file to S3" + e);
        }
    }
}
