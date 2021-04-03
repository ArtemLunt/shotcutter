package com.shotcutter.identity.user;

import com.shotcutter.library.converter.ConverterService;
import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    private UserIdentityService userIdentityService;
    private ConverterService converterService;

    public UserListener(UserIdentityService userIdentityService,
                        ConverterService converterService) {
        this.userIdentityService = userIdentityService;
        this.converterService = converterService;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.REGISTRATION)
    public User registerNewUser(User unregisteredUser) {
        return userIdentityService.registerUser(unregisteredUser)
                .flatMap(user -> converterService.convertTo(user, User.class))
                .get();
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.FIND_BY_ID)
    public User getUserById(String id) {
        var user = userIdentityService.findById(id)
                .flatMap(userDto -> converterService.convertTo(userDto, User.class))
                .get();

        return user;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL)
    public User getUserByEmail(String email) {
        return userIdentityService.findByEmail(email)
                .flatMap(userDto -> converterService.convertTo(userDto, User.class))
                .get();
    }

}
