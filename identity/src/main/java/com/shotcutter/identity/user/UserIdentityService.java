package com.shotcutter.identity.user;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Service
@DependsOn()
public class UserIdentityService {

    @Value("${s3.bucketName}")
    private String s3BucketName;

    private final UserRepository userRepository;
    private final ConverterService converterService;
    private final AmazonS3 amazonS3;

    private final static String USER_AVATAR_PATH_PREFIX = "avatar/user-";

    public UserIdentityService(UserRepository userRepository,
                               ConverterService converterService,
                               AmazonS3 amazonS3) {
        this.userRepository = userRepository;
        this.converterService = converterService;
        this.amazonS3 = amazonS3;
    }

    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> registerUser(User unregisteredUser) {
        return converterService
                .convertTo(unregisteredUser, UserEntity.class)
                .map(userRepository::save);
    }

    public Optional<UserEntity> updateAvatar(String userId, MultipartFile avatar) throws IOException {
        var path = getUserAvatarPath(userId, avatar);
        var user = findById(userId).get();

        // if avatar for this user already exist at s3 - we need to delete it
        if (isCurrentUserAvatarAtS3(user)) {
            var currentAvatarPath = new URL(user.getAvatar()).getPath();
            amazonS3.deleteObject(s3BucketName, currentAvatarPath.substring(1));
        }

        var request = new PutObjectRequest(s3BucketName, path, avatar.getInputStream(), new ObjectMetadata())
                                        .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(request);
        var amazonUrl = amazonS3.getUrl(s3BucketName, path);

        var updatedUser = user.withAvatar(amazonUrl.toString());
        return Optional.ofNullable(userRepository.save(updatedUser));
    }

    public Optional<UserEntity> patch(String userId, UserPatchDTO patchObject) {
        return findById(userId)
                .map(user -> user.withUsername(patchObject.getUsername()))
                .map(userRepository::save);
    }

    private boolean isCurrentUserAvatarAtS3(UserEntity user) {
        return user.getAvatar().contains(s3BucketName) && user.getAvatar().contains(user.getId());
    }

    private String getUserAvatarPath(String userId, MultipartFile file) {
        var fileNameParts = file.getContentType().split("/");
        var fileExtension = fileNameParts[fileNameParts.length - 1];

        return USER_AVATAR_PATH_PREFIX + userId + "_" + file.hashCode() + "." + fileExtension;
    }
}
