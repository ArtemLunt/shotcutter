package com.shotcutter.securitystarter.security;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class JWTAuthConverter implements AuthenticationConverter {

    private final RabbitTemplate rabbitTemplate;

    public JWTAuthConverter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Authentication convert(HttpServletRequest httpServletRequest) {
        Stream<Cookie> cookies = httpServletRequest.getCookies() == null
                ? Stream.empty()
                : Arrays.stream(httpServletRequest.getCookies());

        return cookies
                .filter(cookie -> cookie.getName().equals(CookieKeys.ACCESS_TOKEN.toString()))
                .findFirst()
                .map(Cookie::getValue)
                // if there's a token cookie - we should retrieve the id for this user
                .flatMap(token -> Optional.ofNullable(rabbitTemplate.convertSendAndReceiveAsType(
                        ShotcutterMessageRoutingConstant.Authentication.EXCHANGE_NAME,
                        ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN,
                        token,
                        new ParameterizedTypeReference<String>() {}
                )))
                // then we can fetch the user by id
                .flatMap(id -> Optional.ofNullable(rabbitTemplate.convertSendAndReceiveAsType(
                        ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                        ShotcutterMessageRoutingConstant.User.FIND_BY_ID,
                        id,
                        new ParameterizedTypeReference<User>() {}
                )))
                .map(JWTPrincipal::new)
                .orElse(null);
    }

}
