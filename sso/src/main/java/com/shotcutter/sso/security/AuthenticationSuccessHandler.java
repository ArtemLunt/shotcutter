package com.shotcutter.sso.security;

import com.shotcutter.library.jwt.JWTService;
import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private RabbitTemplate rabbitTemplate;
    private JWTService jwtService;

    AuthenticationSuccessHandler(RabbitTemplate rabbitTemplate,
                                 JWTService jwtService) {
        this.jwtService = jwtService;
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

        User user = (User) rabbitTemplate
                .convertSendAndReceive(
                        ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                        ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL,
                        attributes.get("email")
                );

        // if there's no user with specified email - create a new one
        if (user == null) {
            var newUser = User.builder()
                    .email((String) attributes.get("email"))
                    .avatar((String) attributes.get("picture"))
                    .username((String) attributes.get("name"))
                    .build();

            user = (User) rabbitTemplate.convertSendAndReceive(
                    ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME,
                    ShotcutterMessageRoutingConstant.User.REGISTRATION,
                    newUser
            );
        }

        String accessToken = jwtService.generateToken(user);

        String redirectBase = httpServletRequest
                .getSession()
                .getAttribute(SessionAttributeKey.REDIRECT_URL.toString())
                .toString();

        String redirectUri = UriComponentsBuilder.fromHttpUrl(redirectBase)
                .queryParam(SecurityRequestParam.ACCESS_TOKEN.toString(), accessToken)
                .queryParam(SecurityRequestParam.AUTH_STATUS.toString(), AuthenticationStatus.SUCCESS.toString())
                .toUriString();

        httpServletResponse.sendRedirect(redirectUri);
    }

}
