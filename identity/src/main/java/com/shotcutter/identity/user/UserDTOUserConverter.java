package com.shotcutter.identity.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.shotcutter.library.user.UserDTO;

@Component
public class UserDTOUserConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .avatar(userDTO.getAvatar())
                .email(userDTO.getEmail())
                .build();
    }

}
