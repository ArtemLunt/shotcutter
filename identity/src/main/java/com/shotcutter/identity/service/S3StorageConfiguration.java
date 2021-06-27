package com.shotcutter.identity.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class S3StorageConfiguration {

    private final String accessKeyId;
    private final String secretKey;
    private final String region;

    public S3StorageConfiguration(@Value("${s3.accessKeyId}") String accessKeyId,
                                  @Value("${s3.secretKey}") String secretKey,
                                  @Value("${s3.region}") String region) {
        this.accessKeyId = accessKeyId;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public AmazonS3 initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
