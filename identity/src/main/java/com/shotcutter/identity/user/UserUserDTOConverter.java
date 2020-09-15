package com.shotcutter.identity.user;

import com.shotcutter.library.user.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

}
