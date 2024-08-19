package com.weather.the.what.whattheweather.s3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  @Value("${cloud.aws.cloudfront.url}")
  private String cloudFrontUrl;

  private final S3Client s3Client;

  public String uploadImage(MultipartFile file) throws IOException {
    String uniqueFileName = UUID.randomUUID() + "::WTW-FILE";
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(uniqueFileName.replaceAll("\\s", "_"))
        .contentType(file.getContentType())
        .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

    return cloudFrontUrl + "/" + uniqueFileName;
  }
}
