package com.shotcutter.identity.user;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3control.model.S3CannedAccessControlList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Service
public class UserIdentityService {

    private final String s3BucketName;

    private final UserRepository userRepository;
    private final ConverterService converterService;
    private final S3AsyncClient amazonS3;

    private final static String USER_AVATAR_PATH_PREFIX = "avatar/user-";

    public UserIdentityService(@Value("${s3.bucketName}") String s3BucketName,
                               UserRepository userRepository,
                               ConverterService converterService,
                               S3AsyncClient amazonS3) {
        this.s3BucketName = s3BucketName;
        this.userRepository = userRepository;
        this.converterService = converterService;
        this.amazonS3 = amazonS3;
    }

    public Mono<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserEntity> registerUser(User unregisteredUser) {
        return Mono.just(converterService.convertTo(unregisteredUser, UserEntity.class))
                .flatMap(userRepository::save);
    }

    public Mono<UserEntity> patch(String userId, String newName) {
        return findById(userId)
                .map(user -> user.withUsername(newName))
                .flatMap(userRepository::save);
    }

    public Mono<UserEntity> updateAvatar(String userId, MultipartFile avatar) {
        var path = getUserAvatarPath(userId, avatar);

        var userMono = findById(userId);

        // if avatar for this user already exist at s3 - we need to remove it at first
        userMono.subscribe(this::deleteUserAvatar);

        var putObjReq = PutObjectRequest.builder()
                .bucket(s3BucketName)
                .acl(S3CannedAccessControlList.PUBLIC_READ.toString())
                .key(path)
                .build();

        CompletableFuture<PutObjectResponse> avatarUpdateResponseMono;

        try {
            avatarUpdateResponseMono = amazonS3.putObject(putObjReq, AsyncRequestBody.fromBytes(avatar.getBytes()));
        } catch (IOException e) {
            return Mono.error(new InvalidUserDataException("Provided avatar file is invalid"));
        }

        return Mono.zip(userMono, Mono.fromFuture(avatarUpdateResponseMono))
                .flatMap(tuple -> {
                    var user = tuple.getT1();
                    var getUrlRequest = GetUrlRequest.builder()
                            .bucket(s3BucketName)
                            .key(path)
                            .build();
                    var newAvatarUrl = amazonS3.utilities().getUrl(getUrlRequest).toString();

                    return userRepository.save(user.withAvatar(newAvatarUrl));
                });
    }

    private Mono<DeleteObjectResponse> deleteUserAvatar(UserEntity user) {
        if (user.getAvatar() == null || !user.getAvatar().contains(s3BucketName)) {
            return Mono.empty();
        }

        String avatarPath;

        try {
            avatarPath = new URL(user.getAvatar()).getPath();
        } catch (MalformedURLException e) {
            return Mono.empty();
        }

        var relativeAvatarPath = avatarPath.substring(1);

        var deleteReq = DeleteObjectRequest
                .builder()
                .bucket(s3BucketName)
                .key(relativeAvatarPath)
                .build();

        return Mono.fromFuture(amazonS3.deleteObject(deleteReq));
    }

    private String getUserAvatarPath(String userId, MultipartFile file) {
        // content type has a format such a image/jpeg, here we do need to take an file extension
        var fileContentTypeParts = file.getContentType().split("/");
        var fileExtension = fileContentTypeParts[fileContentTypeParts.length - 1];

        return USER_AVATAR_PATH_PREFIX + userId + "_" + file.hashCode() + "." + fileExtension;
    }

}
