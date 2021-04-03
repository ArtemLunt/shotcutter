package com.shotcutter.identity.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.shotcutter.library.user.User;

@Component
public class UserUserEntityConverter implements Converter<User, UserEntity> {

    @Override
    public UserEntity convert(User user) {
        return UserEntity.userEntityBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .build();
    }

}
