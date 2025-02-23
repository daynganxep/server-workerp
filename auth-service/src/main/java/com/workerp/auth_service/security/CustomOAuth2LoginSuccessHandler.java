package com.workerp.auth_service.security;

import com.workerp.auth_service.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final String clientReceiveRefreshTokenPath = "http://localhost:8081/client/receive-token";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        System.out.println("CustomOAuth2LoginSuccessHandler invoked!");
        if (authentication == null) {
            System.out.println("Authentication is null");
            return;
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();

        if (oauthToken == null) {
            System.out.println("oauthToken is null");
            return;
        }

        System.out.println("OAuth2User: " + oAuth2User.getName());
        String refreshToken = authService.loginOAuth2Success(oAuth2User);

        String redirectUrl = UriComponentsBuilder.fromUriString(clientReceiveRefreshTokenPath)
                .queryParam("refreshToken", refreshToken)
                .toUriString();
        System.out.println("Redirecting to: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
