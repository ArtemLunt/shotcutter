package com.shotcutter.identity.user;

import com.shotcutter.library.user.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityUserConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

}
