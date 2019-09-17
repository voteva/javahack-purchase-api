package com.purchase.admin.security;

import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class NoRedirectStrategy
        implements RedirectStrategy {

    @Override
    public void sendRedirect(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final String url) {
        // No redirect is required with pure REST
    }
}
