package com.shotcutter.securitystarter.security;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
                // if there's a token cookie - we should retrieve the user for this token
                .flatMap(token -> Optional.ofNullable((User) rabbitTemplate.convertSendAndReceive(
                        ShotcutterMessageRoutingConstant.Authentication.EXCHANGE_NAME,
                        ShotcutterMessageRoutingConstant.Authentication.GET_USER_BY_TOKEN,
                        token
                )))
                .map(JWTPrincipal::new)
                .orElse(null);
    }

}
