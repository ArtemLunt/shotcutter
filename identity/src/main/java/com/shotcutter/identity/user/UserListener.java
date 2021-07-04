package com.shotcutter.identity.user;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserListener {

    private final UserIdentityService userIdentityService;
    private final ConverterService converterService;

    public UserListener(UserIdentityService userIdentityService,
                        ConverterService converterService) {
        this.userIdentityService = userIdentityService;
        this.converterService = converterService;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.REGISTRATION)
    public Mono<User> registerNewUser(User unregisteredUser) {
        return userIdentityService.registerUser(unregisteredUser)
                .map(user -> converterService.convertTo(user, User.class))
                .flatMap(Mono::justOrEmpty);
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.FIND_BY_ID)
    public Mono<User> getUserById(String id) {
        return userIdentityService.findById(id)
                .map(userDto -> converterService.convertTo(userDto, User.class))
                .flatMap(Mono::justOrEmpty);
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL)
    public Mono<User> getUserByEmail(String email) {
        return userIdentityService.findByEmail(email)
                .map(userDto -> converterService.convertTo(userDto, User.class))
                .flatMap(Mono::justOrEmpty);
    }

}
