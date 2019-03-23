package com.remote.doctor.auth;

import com.remote.doctor.constant.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class SuccessLoginHandler implements AuthenticationSuccessHandler {
    private static final String ADMIN_REDIRECT = "/admin/main";
    private static final String CLIENT_REDIRECT = "/client/feed";
    private static final String DOCTOR_REDIRECT = "/doctor/feed";

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response, Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = null;

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            if (role.equalsIgnoreCase(Roles.ADMIN.getVal())) {
                redirectUrl = ADMIN_REDIRECT;
            } else if (role.equalsIgnoreCase(Roles.CLIENT.getVal())) {
                redirectUrl = CLIENT_REDIRECT;
            } else if (role.equalsIgnoreCase(Roles.DOCTOR.getVal())) {
                redirectUrl = DOCTOR_REDIRECT;
            } else {
                throw new IllegalArgumentException();
            }
        }

        return redirectUrl;
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
