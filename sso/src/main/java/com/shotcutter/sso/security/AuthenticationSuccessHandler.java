package com.shotcutter.sso.security;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private RabbitTemplate rabbitTemplate;

    AuthenticationSuccessHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Sends redirect with url from the session
     * This redirect is passing to session by next filter:
     *
     * @see RedirectUrlFilter
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication
    ) throws IOException {
        //String baseRedirect;

        var principal = (DefaultOidcUser) authentication.getPrincipal();
        // if redirect url is presented - redirect to this url and attach the token as a get param
        Map<String, Object> attributes = principal.getAttributes();

        UserDTO user = (UserDTO) rabbitTemplate
                .convertSendAndReceive(
                        ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                        ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL,
                        attributes.get("email")
                );

        // if there's no user with specified email - create a new one
        if (user == null) {
            log.info(String.format("There's no user with email %s, registering", attributes.get("email")));

            var newUser = UserDTO.builder()
                    .email((String) attributes.get("email"))
                    .avatar((String) attributes.get("picture"))
                    .username((String) attributes.get("name"))
                    .build();

            user = (UserDTO) rabbitTemplate.convertSendAndReceive(
                    ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                    ShotcutterMessageRoutingConstant.User.REGISTRATION,
                    newUser
            );
        }

        log.info(String.format("User with email %s successfully authenticated", user.getEmail()));
    }

}
