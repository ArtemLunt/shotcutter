package com.shotcutter.identity.user;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.UserDTO;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    private UserIdentityService userIdentityService;

    UserListener(UserIdentityService userIdentityService) {
        this.userIdentityService = userIdentityService;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.REGISTRATION)
    UserDTO registerNewUser(UserDTO userDTO) {
        return userIdentityService.registerUser(userDTO).get();
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL)
    UserDTO getUserByEmail(String email) {
        return userIdentityService.findByEmail(email).get();
    }

}
