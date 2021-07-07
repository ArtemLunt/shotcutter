package com.shotcutter.identity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Component
public class S3StorageConfiguration {

    @Bean
    public S3AsyncClient getAmazonS3Client(@Value("${s3.accessKeyId}") String accessKeyId,
                                           @Value("${s3.secretKey}") String secretKey,
                                           @Value("${s3.region}") String region) {

        var credentials = AwsBasicCredentials.create(accessKeyId, secretKey);
        var credentialsProvider = StaticCredentialsProvider.create(credentials);

        return S3AsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

}
