package com.shotcutter.sso.security;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedirectUrlFilter implements Filter {

    /**
     * We're using this filter to extract the redirect uri from the request
     * and pass it as a session property to keep this uri while redirecting to outer SSO servers
     */
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String redirectUrl = null;

        try {
            redirectUrl = Optional
                    .ofNullable(servletRequest.getParameter(SecurityRequestParam.REDIRECT_TO.toString()))
                    /*.or(() -> {
                        String sessionValue;
                        try {
                            sessionValue = ((HttpServletRequest) servletRequest)
                                    .getSession()
                                    .getAttribute(SecurityRequestParam.REDIRECT_TO.toString())
                                    .toString();
                        } catch (Exception e){
                            return Optional.of(null);
                        }

                        return Optional.ofNullable(sessionValue);
                    })*/
                    .or(() -> {
                        var referer = ((HttpServletRequest) servletRequest).getHeader(HttpHeaders.REFERER);
                        return Optional.ofNullable(referer);
                    })
                    .get();

        } catch (Exception e) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (redirectUrl != null) {
            ((HttpServletRequest) servletRequest).getSession()
                    .setAttribute(SessionAttributeKey.REDIRECT_URL.toString(), redirectUrl);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
