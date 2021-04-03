package com.shotcutter.identity.user;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@DependsOn()
public class UserIdentityService {

    private UserRepository userRepository;
    private ConverterService converterService;

    public UserIdentityService(UserRepository userRepository,
                               ConverterService converterService
    ) {
        this.userRepository = userRepository;
        this.converterService = converterService;
    }

    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> registerUser(User unregisteredUser) {
        var user = converterService.convertTo(unregisteredUser, UserEntity.class).get();
        return Optional.of(userRepository.save(user));
    }

}
