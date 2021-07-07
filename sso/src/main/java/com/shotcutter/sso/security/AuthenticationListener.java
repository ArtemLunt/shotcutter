package com.shotcutter.sso.security;

import com.shotcutter.library.jwt.JWTService;
import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    private final JWTService jwtService;

    public AuthenticationListener(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @RabbitListener(queues = ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN)
    public String getUserByToken(String accessToken) {
        return jwtService.getUserIdByToken(accessToken);
    }

}
