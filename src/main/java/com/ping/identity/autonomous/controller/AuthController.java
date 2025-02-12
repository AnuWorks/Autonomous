package com.ping.identity.autonomous.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(
            @AuthenticationPrincipal OAuth2User user,
            @RequestParam(required = false) String provider) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Unauthorized"));
        }

        Map<String, String> response = new HashMap<>();

        if (user instanceof OidcUser oidcUser) {
            response.put("token", oidcUser.getIdToken().getTokenValue());
            response.put("provider", provider != null ? provider : "Google");
        } else {
            response.put("name", user.getAttribute("name"));
            response.put("email", user.getAttribute("email"));
            response.put("provider", provider != null ? provider : "GitHub");
        }

        return ResponseEntity.ok(response);
    }

    // New endpoint to get the access token
    @GetMapping("/access-token")
    public ResponseEntity<Map<String, String>> getAccessToken(OAuth2AuthenticationToken authenticationToken) {
        if (authenticationToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Unauthorized"));
        }

        OAuth2User user = authenticationToken.getPrincipal();
        if (user instanceof OidcUser oidcUser) {  // If using OIDC
            String provider = authenticationToken.getAuthorizedClientRegistrationId();
            Map<String, String> response = new HashMap<>();
            String jwtToken = oidcUser.getIdToken().getTokenValue();
            response.put("provider", provider);
            response.put("access_token", jwtToken);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Invalid authentication type"));
    }

}
