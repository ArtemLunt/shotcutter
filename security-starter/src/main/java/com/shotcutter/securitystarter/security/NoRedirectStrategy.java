package com.shotcutter.securitystarter.security;

import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class NoRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             String s) throws IOException {
    }

}
