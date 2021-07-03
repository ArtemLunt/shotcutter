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

    private final String s3BucketName;

    private final UserRepository userRepository;
    private final ConverterService converterService;
    private final AmazonS3 amazonS3;

    private final static String USER_AVATAR_PATH_PREFIX = "avatar/user-";

    public UserIdentityService(@Value("${s3.bucketName}") String s3BucketName,
                               UserRepository userRepository,
                               ConverterService converterService,
                               AmazonS3 amazonS3) {
        this.s3BucketName = s3BucketName;
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

        // if avatar for this user already exist at s3 - we need to remove it at first
        if (user.getAvatar() != null) {
            var avatarPath = new URL(user.getAvatar()).getPath();
            var relativeAvatarPath = avatarPath.substring(1);

            if (amazonS3.doesObjectExist(s3BucketName, relativeAvatarPath)) {
                amazonS3.deleteObject(s3BucketName, relativeAvatarPath);
            }
        }

        var request = new PutObjectRequest(s3BucketName, path, avatar.getInputStream(), new ObjectMetadata())
                                        .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(request);
        var amazonUrl = amazonS3.getUrl(s3BucketName, path);

        var updatedUser = user.withAvatar(amazonUrl.toString());
        return Optional.ofNullable(userRepository.save(updatedUser));
    }

    public Optional<UserEntity> patch(String userId, String newName) {
        return findById(userId)
                .map(user -> user.withUsername(newName))
                .map(userRepository::save);
    }

    private String getUserAvatarPath(String userId, MultipartFile file) {
        // content type has a format such a image/jpeg, here we do need to take an file extension
        var fileContentTypeParts = file.getContentType().split("/");
        var fileExtension = fileContentTypeParts[fileContentTypeParts.length - 1];

        return USER_AVATAR_PATH_PREFIX + userId + "_" + file.hashCode() + "." + fileExtension;
    }
}
