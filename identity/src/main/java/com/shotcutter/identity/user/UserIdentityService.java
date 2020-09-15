package com.shotcutter.identity.user;

import com.shotcutter.library.converter.ConverterRequiredException;
import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserIdentityService {
    private UserRepository userRepository;
    private ConverterService converterService;

    UserIdentityService(UserRepository userRepository,
                        ConverterService converterService
    ) throws ConverterRequiredException {
        if (!converterService.hasTwoWayConverter(User.class, UserDTO.class)) {
            throw new ConverterRequiredException(User.class, UserDTO.class);
        }

        this.userRepository = userRepository;
        this.converterService = converterService;
    }

    Optional<UserDTO> findByEmail(String email) {
        log.info(String.format("Looking for the user with email %s", email));
        return userRepository.findByEmail(email)
                .flatMap(userDto -> converterService.convertTo(userDto, UserDTO.class));
    }

    Optional<UserDTO> registerUser(UserDTO userDTO) {
        log.info(String.format("Register for the user %s", userDTO.toString()));
        return Optional.of(userRepository.save(converterService.convertTo(userDTO, User.class).get()))
                .flatMap(user -> converterService.convertTo(user, UserDTO.class));
    }
}
