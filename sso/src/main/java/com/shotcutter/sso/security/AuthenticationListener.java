package com.shotcutter.sso.security;

import com.shotcutter.library.jwt.JWTService;
import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    private final RabbitTemplate rabbitTemplate;
    private final JWTService jwtService;

    public AuthenticationListener(RabbitTemplate rabbitTemplate, JWTService jwtService) {
        this.rabbitTemplate = rabbitTemplate;
        this.jwtService = jwtService;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.Authentication.GET_USER_BY_TOKEN)
    public User getUserByToken(String accessToken) {
        String userId = jwtService.getUserIdByToken(accessToken);
        return (User) rabbitTemplate.convertSendAndReceive(
                ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                ShotcutterMessageRoutingConstant.User.FIND_BY_ID,
                userId
        );
    }

}
